package com.fzj.alg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fzj.config.NameSpace;
import com.fzj.model.SSModel;
import com.fzj.solution.ASolution;
import com.fzj.solution.FireSparkSolution;
import com.fzj.solution.Fitness;
import com.fzj.solution.ParticleSolution;
import com.fzj.solution.WaveSolution;
import com.fzj.utils.MathUtils;

/** 
 * @author Fan Zhengjie 
 * @date 2017年1月28日 下午2:50:40 
 * @version 1.0 
 * @description 抽象策略类
 */
public abstract class AStrategy {

	protected String m_str_data_path;//实验数据路径，例：data_01\\
	protected String m_str_alg_name;//算法名，当前使用何种算法，例：PSO
	protected String m_str_file_name;//保存文件的名字
	
	protected int m_aI4_max_iter;//最大进化次数
	protected int m_aI4_max_nfe;//最大评价次数
	
	protected int m_aI4_size;//种群规模
	protected int m_aI4_d;//问题维度
	protected int m_aI4_cur_iter;//当前进化代数
	protected int m_aI4_cur_nfe;//当前评价次数
	
	protected SSModel m_aTC_ssm;//安检设备调度模型
	protected int m_aI4_n;//危险物品种类数
	protected int m_aI4_m;//安检设备种类数
	protected int m_aI4_r;//安检场所数
	protected double m_rI8_wi[];//危险物品的权重
	protected double m_rI8_fik[][];//危险物品i在k出现的频率
	protected double m_rI8_dtij[][];//物品i由设备j检测所需的时间
	protected double m_rI8_dcij[][];//危险物品i被设备j检测到的概率
	protected double m_rI8_dlk[];//安检场所k处的累积损耗时间
	
	protected Random m_aTC_random;//随机数生成器
	protected int m_aI4_low;//约束下限
	protected int m_aI4_upper;//约束上限
	
	protected List<Fitness> m_aTC_listOfFitness;//用于结果的保存显示
	protected ASolution[] m_aTC_population;//种群
	
	protected int[] m_rI4_num_device_j;//各类设备的数量（此处每类设备2r个，总设备数2mr个）
	protected int m_aI4_num_device_total;//各类设备的总数量
	
	
	public AStrategy(){}
	
	public AStrategy(int f_aI4_size,int f_aI4_max_nfe,int f_aI4_max_iter,SSModel f_aTC_ssm,String f_str_data_path){
		this.m_aI4_size = f_aI4_size;
		this.m_aI4_max_nfe = f_aI4_max_nfe;
		this.m_aI4_max_iter = f_aI4_max_iter;
		this.m_aTC_ssm = f_aTC_ssm;
		this.m_str_data_path = f_str_data_path;
		
		this.m_aI4_cur_iter = 0;
		this.m_aI4_cur_nfe = 0;
		
		this.m_aI4_d = m_aTC_ssm.getM_aI4_d();
		this.m_aI4_n = m_aTC_ssm.getM_aI4_n();
		this.m_aI4_m = m_aTC_ssm.getM_aI4_m();
		this.m_aI4_r = m_aTC_ssm.getM_aI4_r();
		this.m_rI8_dcij = m_aTC_ssm.getM_rI8_dcij();
		this.m_rI8_dlk = m_aTC_ssm.getM_rI8_dlk();
		this.m_rI8_dtij = m_aTC_ssm.getM_rI8_dtij();
		this.m_rI8_fik = m_aTC_ssm.getM_rI8_fik();
		this.m_rI8_wi = m_aTC_ssm.getM_rI8_wi();
		
		this.m_aTC_random = new Random(System.currentTimeMillis());
		this.m_aI4_low = 0;
		this.m_aI4_upper = m_aI4_r + 1;
		
		this.m_aTC_listOfFitness = new ArrayList<>();
		this.m_rI4_num_device_j = new int[m_aI4_m];
		for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
			m_rI4_num_device_j[t_aI4_j] = 2*m_aI4_r;
		}
		this.m_aI4_num_device_total = 2*m_aI4_m*m_aI4_r;
	}
	
	/**
	 * 参数初始化
	 */
	protected abstract void init();
	
	/**
	 * 种群初始化
	 */
	protected void initPopulation(String f_str_alg_name){
		for(int t_aI4_k=0;t_aI4_k<m_aI4_size;t_aI4_k++){
			ASolution t_aTC_solution = null;
			if(f_str_alg_name.equals(NameSpace.s_str_pso) || f_str_alg_name.equals(NameSpace.s_str_dnspso)){
				t_aTC_solution = new ParticleSolution(m_aI4_d);
			}else if(f_str_alg_name.equals(NameSpace.s_str_fade)){
				t_aTC_solution = new FireSparkSolution(m_aI4_d);
			}else if(f_str_alg_name.equals(NameSpace.s_str_wwo) || f_str_alg_name.equals(NameSpace.s_str_pwwo)){
				t_aTC_solution = new WaveSolution(m_aI4_d);
			}
			int t_aI4_total_x = 0;
			int[] t_rI4_num_device_j = m_rI4_num_device_j.clone();
			//每类设备有2R个，总数2MR个
			int[] t_rI4_x = new int[m_aI4_d];
			int[] t_rI4_device_j = new int[m_aI4_m];
			for(int t_aI4_i=0;t_aI4_i<m_aI4_d;t_aI4_i++){
				int t_aI4_device_type = t_aI4_i % m_aI4_m;//设备类型
				int t_aI4_device_area = t_aI4_i / m_aI4_m;//设备区域
				if(t_aI4_device_area < m_aI4_r-1){
					if(t_rI4_num_device_j[t_aI4_device_type]<=0){
						t_rI4_x[t_aI4_i] = 0;
					}else {
						int t_aI4_num_j = MathUtils.getIntAtoB(m_aI4_low, m_aI4_upper);
						while(t_rI4_num_device_j[t_aI4_device_type]-t_aI4_num_j<0){
							t_aI4_num_j = MathUtils.getIntAtoB(m_aI4_low, m_aI4_upper);
						}
						t_rI4_x[t_aI4_i] = t_aI4_num_j;
						t_rI4_num_device_j[t_aI4_device_type] -= t_aI4_num_j;
					}
				}else {
					t_rI4_x[t_aI4_i] = t_rI4_num_device_j[t_aI4_device_type];
				}	
				t_rI4_device_j[t_aI4_device_type] += t_rI4_x[t_aI4_i];
				t_aI4_total_x += t_rI4_x[t_aI4_i];
			}
			
			t_aTC_solution.setM_rI4_x(t_rI4_x);
			t_aTC_solution.setM_rI4_device_j(t_rI4_device_j);
			t_aTC_solution.setM_aI4_total_x(t_aI4_total_x);
			m_aTC_population[t_aI4_k] = t_aTC_solution;
			evaluate(m_aTC_population[t_aI4_k]);
		}
	}
	
	/**
	 * 每隔50次评价保存适应度
	 * @param f_aTC_solution
	 */
	protected void saveFitness(ASolution f_aTC_solution){
		if(m_aI4_cur_nfe % 50 == 0 || m_aI4_cur_nfe == (m_aI4_max_nfe-1)){
			Fitness t_aTC_fitness = new Fitness(m_aI4_cur_nfe, f_aTC_solution.getM_aI8_fitness());
			m_aTC_listOfFitness.add(t_aTC_fitness);
		}
	}
	
	/**
	 * 适应度计算
	 */
	protected void evaluate(ASolution f_aTC_solution){
		int[] t_rI4_x = f_aTC_solution.getM_rI4_x();
		double t_aI8_fitness = 0.0;
		double t_aI8_detect_time,t_aI8_wait_time,t_aI8_p;
		for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
			for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
				int t_aI4_pos = t_aI4_k * m_aI4_m + t_aI4_j;
				for(int t_aI4_i=0;t_aI4_i<m_aI4_n;t_aI4_i++){
					//计算Pijk
				    t_aI8_detect_time = t_rI4_x[t_aI4_pos] * m_rI8_dtij[t_aI4_i][t_aI4_j] * m_rI8_dcij[t_aI4_i][t_aI4_j];	
				    if(t_aI4_i==0)
				    	t_aI8_wait_time = 0;
				    else
				    	t_aI8_wait_time = t_rI4_x[t_aI4_pos] * m_rI8_dtij[t_aI4_i-1][t_aI4_j] * m_rI8_dcij[t_aI4_i-1][t_aI4_j];
				    t_aI8_p = t_aI8_detect_time / (t_aI8_wait_time + m_rI8_dlk[t_aI4_k] + t_aI8_detect_time);
					t_aI8_fitness += m_rI8_wi[t_aI4_i] * m_rI8_fik[t_aI4_i][t_aI4_k] * t_aI8_p;
				}
			}
		}
		f_aTC_solution.setM_cur_nfe(m_aI4_cur_nfe);
		f_aTC_solution.setM_cur_iter(m_aI4_cur_iter);
		f_aTC_solution.setM_aI8_fitness(t_aI8_fitness);
	}
	
	/**
	 * 种群进化
	 */
	protected abstract void evolution();
	
	/**
	 * 问题求解
	 */
	protected abstract ASolution solve(int f_aI4_p);
	
	/**
	 * 解的修正
	 * @param f_aTC_solution
	 */
	protected void modify(ASolution f_aTC_solution){
		calNumOfDevice(f_aTC_solution);
		int[] t_rI4_num_device_j = f_aTC_solution.getM_rI4_device_j();//各类设备的数量
		int[] t_rI4_x = f_aTC_solution.getM_rI4_x();
		
		boolean[] t_rI1_ok = new boolean[m_aI4_m];//各类设备是否修正完毕
		int[] t_rI4_j_cha = new int[m_aI4_m];//各类设备增加或减少的个数
		for(int t_aI4_i=0;t_aI4_i<m_aI4_m;t_aI4_i++){
			t_rI4_j_cha[t_aI4_i] = t_rI4_num_device_j[t_aI4_i] - m_rI4_num_device_j[t_aI4_i];
			t_rI1_ok[t_aI4_i] = false;//默认非修正状态（false）
		}
		
		int t_aI4_ok = 0;//修正的个数
		while(t_aI4_ok!=m_aI4_m){
			//System.out.println("t_aI4_ok = "+t_aI4_ok);
			int t_aI4_pos = m_aTC_random.nextInt(m_aI4_d);
			int t_aI4_device_type = t_aI4_pos % m_aI4_m;//设备类型
			if(t_rI4_j_cha[t_aI4_device_type]==0){
				if(t_rI1_ok[t_aI4_device_type]==false){
					t_rI1_ok[t_aI4_device_type] = true;
					t_aI4_ok++;
				}
			}
			else if(t_rI4_j_cha[t_aI4_device_type]<0){
				if(t_rI4_x[t_aI4_pos]<m_aI4_upper){
					t_rI4_x[t_aI4_pos]++;
					t_rI4_j_cha[t_aI4_device_type]++;
				}
			}
			else {
				if(t_rI4_x[t_aI4_pos]>0){
					t_rI4_x[t_aI4_pos]--;
					t_rI4_j_cha[t_aI4_device_type]--;
				}
			}
		}
		calNumOfDevice(f_aTC_solution);
	}
	
	/**
	 * 计算各类设备的数量和设备总数量
	 * @param f_aTC_solution
	 */
	protected void calNumOfDevice(ASolution f_aTC_solution){
		int[] t_rI4_x = f_aTC_solution.getM_rI4_x();
		int[] t_rI4_num_device_j = new int[m_aI4_m];
		int t_aI4_num_device_total = 0;
		for(int t_aI4_i=0;t_aI4_i<m_aI4_m;t_aI4_i++){
			t_rI4_num_device_j[t_aI4_i] = 0;
		}
		for(int t_aI4_i=0;t_aI4_i<m_aI4_d;t_aI4_i++){
			int t_aI4_device_type = t_aI4_i % m_aI4_m;//设备类型
			t_rI4_num_device_j[t_aI4_device_type] += t_rI4_x[t_aI4_i];
			t_aI4_num_device_total += t_rI4_x[t_aI4_i];
		}
		f_aTC_solution.setM_aI4_total_x(t_aI4_num_device_total);
		f_aTC_solution.setM_rI4_device_j(t_rI4_num_device_j);
	}
	
	/**
	 * 最优解的结果获取
	 * @param f_aI4_p
	 * @param f_aTC_solution 最优解
	 * @return
	 */
	protected String getResultContent(int f_aI4_p,ASolution f_aTC_solution){
		String t_str_result = "";
		int[] t_rI4_device_j = f_aTC_solution.getM_rI4_device_j();//各类设备的数量
		int[] t_rI4_x = f_aTC_solution.getM_rI4_x();//分配方案
		t_str_result += "第"+f_aI4_p+"个最优解：\nIter = "+f_aTC_solution.getM_cur_iter()+"\nNfe = "+f_aTC_solution.getM_cur_nfe()
			+"\nFitness = "+f_aTC_solution.getM_aI8_fitness() + "\nThe number of total device = "+f_aTC_solution.getM_aI4_total_x();
		t_str_result += "\nThe number of various devices = ";
		for(int t_aI4_i=0;t_aI4_i<m_aI4_m;t_aI4_i++){
			t_str_result += t_rI4_device_j[t_aI4_i];
			if(t_aI4_i<m_aI4_m-1)
				t_str_result += " ";
		}
		t_str_result += "\nThe scheme of allocation = ";
		for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
			for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
				t_str_result += t_rI4_x[t_aI4_k*m_aI4_m+t_aI4_j];
				if(t_aI4_j<m_aI4_m-1)
					t_str_result += " ";
				else if(t_aI4_k<m_aI4_r-1)
					t_str_result += "|";
				else
					t_str_result += "\n\n";	
			}
		}
		return t_str_result;
	}
	
	/**
	 * 种群打印函数（用于调试）
	 */
	protected String printPopulation(ASolution[] f_aTC_solution){
		String t_str_res = "";//结果，用于测试
		for(int t_aI4_i = 0;t_aI4_i<m_aI4_size;t_aI4_i++){
			ASolution t_aTC_solution = f_aTC_solution[t_aI4_i];
			int[] t_rI4_x = t_aTC_solution.getM_rI4_x();
			for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
				for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
					//System.out.print(t_rI4_x[t_aI4_k*m_aI4_m+t_aI4_j]);
					t_str_res += t_rI4_x[t_aI4_k*m_aI4_m+t_aI4_j];
					if(t_aI4_j<m_aI4_m-1){
						//System.out.print(" ");
						t_str_res += " ";
					}		
					else if(t_aI4_k<m_aI4_r-1){
						//System.out.print("|");
						t_str_res += "|";
					}	
					else{
						//System.out.println();
						t_str_res += "\n";
						int[] t_rI4_device_j = t_aTC_solution.getM_rI4_device_j();
						for(int t_aI4_t=0;t_aI4_t<m_aI4_m;t_aI4_t++){
							//System.out.print("deveice_"+t_aI4_t+" = "+t_rI4_device_j[t_aI4_t]+"|");
							t_str_res += "deveice_"+t_aI4_t+" = "+t_rI4_device_j[t_aI4_t]+"|";
						}
						//System.out.println("total_x = "+t_aTC_solution.getM_aI4_total_x()+"|fitness = "+t_aTC_solution.getM_aI8_fitness());
						t_str_res += "total_x = "+t_aTC_solution.getM_aI4_total_x()+"|fitness = "+t_aTC_solution.getM_aI8_fitness()+"\n";
					}		
				}
			}
		}
		return t_str_res;
	}
	
	/**
	 * 打印函数（用于调试）
	 */
	protected void printPopulation(List<ASolution> f_aTC_solution){
		for(int t_aI4_i = 0;t_aI4_i<f_aTC_solution.size();t_aI4_i++){
			ASolution t_aTC_solution = f_aTC_solution.get(t_aI4_i);
			int[] t_rI4_x = t_aTC_solution.getM_rI4_x();
			for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
				for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
					System.out.print(t_rI4_x[t_aI4_k*m_aI4_m+t_aI4_j]);
					if(t_aI4_j<m_aI4_m-1)
						System.out.print(" ");
					else if(t_aI4_k<m_aI4_r-1)
						System.out.print("|");
					else{
						System.out.println();
						int[] t_rI4_device_j = t_aTC_solution.getM_rI4_device_j();
						for(int t_aI4_t=0;t_aI4_t<m_aI4_m;t_aI4_t++){
							System.out.print("deveice_"+t_aI4_t+" = "+t_rI4_device_j[t_aI4_t]+"|");
						}
						System.out.println("total_x = "+t_aTC_solution.getM_aI4_total_x()+"|fitness = "+t_aTC_solution.getM_aI8_fitness());
					}
						
				}
			}
		}
	}
	
	/**
	 * 单个解的打印函数（用于调试）
	 */
	protected void printSolution(ASolution f_aTC_solution){
		int[] t_rI4_x = f_aTC_solution.getM_rI4_x();
		for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
			for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
				System.out.print(t_rI4_x[t_aI4_k*m_aI4_m+t_aI4_j]);
				if(t_aI4_j<m_aI4_m-1)
					System.out.print(" ");
				else if(t_aI4_k<m_aI4_r-1)
					System.out.print("|");
				else{
					System.out.println();
					int[] t_rI4_device_j = f_aTC_solution.getM_rI4_device_j();
					for(int t_aI4_t=0;t_aI4_t<m_aI4_m;t_aI4_t++){
						System.out.print("deveice_"+t_aI4_t+" = "+t_rI4_device_j[t_aI4_t]+"|");
					}
					System.out.println("total_x = "+f_aTC_solution.getM_aI4_total_x()+"|fitness = "+f_aTC_solution.getM_aI8_fitness());
				}	
			}
		}
	}

	
}
