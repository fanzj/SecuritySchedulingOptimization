package com.fzj.alg;

import java.util.ArrayList;
import java.util.List;

import com.fzj.config.NameSpace;
import com.fzj.model.SSModel;
import com.fzj.solution.ASolution;
import com.fzj.solution.Fitness;
import com.fzj.utils.FileUtils;
import com.fzj.utils.TimeUtils;
import com.fzj.utils.excel.ExcelUtil;

/** 
 * @author Fan Zhengjie 
 * @date 2017年2月2日 下午9:59:39 
 * @version 1.0 
 * @description 算法运行类
 */
public class AlgRun {
	
	private String m_str_alg_type;//算法类型
	private String m_str_data_path;//保存路径
	private int m_aI4_size;//种群大小
	private int m_aI4_max_nfe;//评价次数
	private int m_aI4_max_iter;//迭代次数
	private String m_str_result_name;//结果名称
	
	public AlgRun(){}
	
	public AlgRun(String f_str_alg_type,String f_str_data_path,int f_aI4_size,int f_aI4_max_nfe,int f_aI4_max_iter){
		this.m_str_alg_type = f_str_alg_type;
		this.m_str_data_path = f_str_data_path;
		this.m_aI4_size = f_aI4_size;
		this.m_aI4_max_nfe = f_aI4_max_nfe;
		this.m_aI4_max_iter = f_aI4_max_iter;
		this.m_str_result_name = FileUtils.getResultName(m_str_alg_type, NameSpace.s_str_file_excel,f_aI4_max_nfe);
	}
	
	/**
	 * 算法运行
	 * @param f_aI4_run_times 运行次数
	 */
	public void algRun(int f_aI4_run_times){
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		System.out.println(m_str_alg_type+"算法开始求解安检设备调度问题,请等待...");
		SSModel t_aTC_ssm = null;
		synchronized (AlgRun.class) {
			t_aTC_ssm = new SSModel(m_str_data_path);
		}
		
		FileUtils.s_aI1_operation = false;//保存到result
		AStrategy t_aTC_strategy = null;
		ASolution t_aTC_best_solution = null,t_aTC_best = null;
		double t_aI8_sum = 0.0;
		double t_aI8_fmin = Double.MAX_VALUE;
		double t_aI8_fmax = Double.MIN_VALUE;
		List<ASolution> t_aTC_results = new ArrayList<>();
		List<Fitness> t_aTC_minBest = new ArrayList<>();
		for(int t_aI4_i=0;t_aI4_i<f_aI4_run_times;t_aI4_i++){
			if(m_str_alg_type.equals(NameSpace.s_str_pso)){
				t_aTC_strategy = new PSOStrategy(m_aI4_size, m_aI4_max_nfe, m_aI4_max_iter, t_aTC_ssm, m_str_data_path);
			}else if (m_str_alg_type.equals(NameSpace.s_str_dnspso)) {
				t_aTC_strategy = new DNSPSOStrategy(m_aI4_size, m_aI4_max_nfe, m_aI4_max_iter, t_aTC_ssm, m_str_data_path);
			}else if(m_str_alg_type.equals(NameSpace.s_str_fade)){
				t_aTC_strategy = new FireSparkStrategy(m_aI4_size, m_aI4_max_nfe, m_aI4_max_iter, t_aTC_ssm, m_str_data_path);
			}else if(m_str_alg_type.equals(NameSpace.s_str_wwo)){
				t_aTC_strategy = new WWOStrategy(m_aI4_size, m_aI4_max_nfe, m_aI4_max_iter, t_aTC_ssm, m_str_data_path);
			}else if(m_str_alg_type.equals(NameSpace.s_str_pwwo)){
				t_aTC_strategy = new PWWOStrategy(m_aI4_size, m_aI4_max_nfe, m_aI4_max_iter, t_aTC_ssm, m_str_data_path);
			}else if(m_str_alg_type.equals(NameSpace.s_str_dednspso)){
				t_aTC_strategy = new DE_DNSPSOStrategy(m_aI4_size, m_aI4_max_nfe, m_aI4_max_iter, t_aTC_ssm, m_str_data_path);
			}
			t_aTC_best_solution = t_aTC_strategy.solve(t_aI4_i+1);
			double t_aI4_best_fitness = t_aTC_best_solution.getM_aI8_fitness();
			t_aI8_sum += t_aI4_best_fitness;
			if (Double.compare(t_aI8_fmin, t_aI4_best_fitness)>0) {
				t_aI8_fmin = t_aI4_best_fitness;
				t_aTC_minBest.clear();
				t_aTC_minBest.addAll(t_aTC_strategy.m_aTC_listOfFitness);
			}
			if (Double.compare(t_aI8_fmax, t_aI4_best_fitness)<0) {
				t_aI8_fmax = t_aI4_best_fitness;
				t_aTC_best = t_aTC_best_solution;
			}
			t_aTC_results.add(t_aTC_best_solution);
		}
		
		//将迭代过程保存到excel中
		{
			try {
				new ExcelUtil().writeExcel(t_aTC_minBest, FileUtils.getPath(m_str_data_path, m_str_alg_type)+m_str_result_name, m_str_alg_type);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		{
			int[] t_rI4_x = t_aTC_best.getM_rI4_x();
			int[] t_rI4_device_j = t_aTC_best.getM_rI4_device_j();
			int t_aI4_m = t_aTC_strategy.m_aI4_m;
			int t_aI4_r = t_aTC_strategy.m_aI4_r;
			double t_aI8_std = 0.0;//标准差
			double t_aI8_mean = t_aI8_sum/f_aI4_run_times;
			String t_str_result = "The final result:\n";
			t_str_result += "fmax = "+t_aI8_fmax+"\nfmin = "+t_aI8_fmin+"\nfmean = "+t_aI8_mean+"\n";
			for(int t_aI4_i=0;t_aI4_i<f_aI4_run_times;t_aI4_i++){
				ASolution t_aTC_solution = t_aTC_results.get(t_aI4_i);
				t_aI8_std += (t_aTC_solution.getM_aI8_fitness()-t_aI8_mean) * (t_aTC_solution.getM_aI8_fitness()-t_aI8_mean);
			}
			t_str_result += "standard std = "+Math.sqrt(t_aI8_std/(f_aI4_run_times-1))+"\n";
			t_str_result += "The number of total device = "+ t_aTC_best.getM_aI4_total_x()+"\n";
			t_str_result += "The number of various devices = ";
			for(int t_aI4_i=0;t_aI4_i<t_aI4_m;t_aI4_i++){
				t_str_result += t_rI4_device_j[t_aI4_i];
				if(t_aI4_i<t_aI4_m-1)
					t_str_result += " ";
				else
					t_str_result += "\n";
			}
			t_str_result += "The optimal allocation = ";	
			for(int t_aI4_k=0;t_aI4_k<t_aI4_r;t_aI4_k++){
				for(int t_aI4_j=0;t_aI4_j<t_aI4_m;t_aI4_j++){
					t_str_result += t_rI4_x[t_aI4_k*t_aI4_m+t_aI4_j];
					if(t_aI4_j<t_aI4_m-1)
						t_str_result += " ";
					else if(t_aI4_k<t_aI4_r-1)
						t_str_result += "|";
					else
						t_str_result += "\n";	
				}
			}
			
			FileUtils.saveFile(m_str_data_path, m_str_alg_type, t_aTC_strategy.m_str_file_name, t_str_result);
		}
			
		System.out.println(m_str_alg_type+"算法求解安检设备调度问题结束！当前时间为："+TimeUtils.getCurrentTime());
	}
}
