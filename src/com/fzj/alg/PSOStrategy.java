package com.fzj.alg;

import javax.tools.Diagnostic;

import com.fzj.config.NameSpace;
import com.fzj.model.SSModel;
import com.fzj.solution.ASolution;
import com.fzj.solution.Fitness;
import com.fzj.solution.ParticleSolution;
import com.fzj.utils.FileUtils;

/** 
 * @author Fan Zhengjie 
 * @date 2017��1��28�� ����2:51:14 
 * @version 1.0 
 * @description PSO(����Ⱥ�㷨)
 */
public class PSOStrategy extends AStrategy{
	
	private double m_aI8_c1;//ѧϰ����
	private double m_aI8_c2;//ѧϰ����
	private int m_aI4_vmax;//����ٶ�
	private int m_aI4_vmin;
	private int m_aI4_xmax;//λ������
	private int m_aI4_xmin;
	private double m_aI8_w;//����Ȩ��
	private double m_aI8_wmin;//��СȨ��ϵ��
	private double m_aI8_wmax;//���Ȩ��ϵ��
	public ParticleSolution m_aTC_gBest;//ȫ����������
	private ParticleSolution[] m_aTC_pBest;//��ʷ��������
		
	public PSOStrategy() {
		super();
	}
	
	public PSOStrategy(int f_aI4_size,int f_aI4_max_nfe,int f_aI4_max_iter,SSModel f_aTC_ssm,String f_str_data_path){
		super(f_aI4_size, f_aI4_max_nfe, f_aI4_max_iter,f_aTC_ssm,f_str_data_path);
	}
	
	@Override
	protected void init() {
		this.m_aI8_c1 = 2;
		this.m_aI8_c2 = 2;
		this.m_aI8_wmin = 0.4;
		this.m_aI8_wmax = 0.9;
		
		this.m_aI8_w = m_aI8_wmax;
		this.m_aTC_population = new ParticleSolution[m_aI4_size];
	
		this.m_aI4_vmax = m_aI4_upper - 1;
		this.m_aI4_vmin = 0;
		this.m_aI4_xmax = m_aI4_upper;
		this.m_aI4_xmin = 0;
		
		m_str_alg_name = NameSpace.s_str_pso;
		m_str_file_name = FileUtils.getResultName(m_str_alg_name, NameSpace.s_str_file_txt,m_aI4_max_nfe);
		
	}
	
	/**
	 * ����Ȩ�صĸ���
	 */
	private void updateW(){
		m_aI8_w = m_aI8_wmax - ((double) m_aI4_cur_iter / (double) m_aI4_max_iter) * (m_aI8_wmax - m_aI8_wmin);
	}
	
	/**
	 * ��ʼ����Ⱥ��λ�Ƶĳ�ʼ��
	 */
	private void initV(){
		for(int t_aI4_k=0;t_aI4_k<m_aI4_size;t_aI4_k++){
			ParticleSolution t_aTC_particle = (ParticleSolution) m_aTC_population[t_aI4_k];
			int[] t_aI4_x = t_aTC_particle.getM_rI4_x();
			t_aTC_particle.setM_rI4_v(t_aI4_x);
		}
	}
	/**
	 * ȫ�����ź���ʷ�������ӵĸ���
	 */
	private void initBest(){
		this.m_aTC_pBest = new ParticleSolution[m_aI4_size];
		this.m_aTC_gBest = new ParticleSolution(m_aI4_d);
		m_aTC_gBest.setM_aI8_fitness(Double.MIN_VALUE);
		
		for(int t_aI4_k=0;t_aI4_k<m_aI4_size;t_aI4_k++){
			m_aTC_pBest[t_aI4_k] = ((ParticleSolution) (m_aTC_population[t_aI4_k])).clone();
			if(Double.compare(m_aTC_gBest.getM_aI8_fitness(), m_aTC_pBest[t_aI4_k].getM_aI8_fitness())<0){
				m_aTC_gBest = (ParticleSolution) m_aTC_pBest[t_aI4_k].clone();
			}
		}
	}
	
	/**
	 * ����pBest��gBest
	 * @param f_aI4_k
	 */
	private void updateBest(int f_aI4_k){
		if(Double.compare(m_aTC_pBest[f_aI4_k].getM_aI8_fitness(), m_aTC_population[f_aI4_k].getM_aI8_fitness())<0){
			m_aTC_pBest[f_aI4_k] = ((ParticleSolution) m_aTC_population[f_aI4_k]).clone();
		}
		if(Double.compare(m_aTC_gBest.getM_aI8_fitness(), m_aTC_pBest[f_aI4_k].getM_aI8_fitness())<0){
			m_aTC_gBest = m_aTC_pBest[f_aI4_k].clone();
		}
	}
	
	/**
	 * �����ٶ�λ��
	 * @param f_aTC_solution
	 * @param f_aI4_index
	 */
	private void updateXV(ParticleSolution f_aTC_solution,int f_aI4_index){
		double t_aI8_r1,t_aI8_r2;
		int[] t_rI4_xp = m_aTC_pBest[f_aI4_index].getM_rI4_x();
		int[] t_rI4_xg = m_aTC_gBest.getM_rI4_x();
		int[] t_rI4_cur_x = f_aTC_solution.getM_rI4_x();
		int[] t_rI4_cur_v = f_aTC_solution.getM_rI4_v();
		int[] t_rI4_new_x = new int[m_aI4_d];
		int[] t_rI4_new_v = new int[m_aI4_d];
		for(int t_aI4_j=0;t_aI4_j<m_aI4_d;t_aI4_j++){
			t_aI8_r1 = m_aTC_random.nextDouble();
			t_aI8_r2 = m_aTC_random.nextDouble();
			double t_aI8_temp_v = m_aI8_w * t_rI4_cur_v[t_aI4_j] + m_aI8_c1 * t_aI8_r1 * (t_rI4_xp[t_aI4_j] - t_rI4_cur_x[t_aI4_j]) 
					+ m_aI8_c2 * t_aI8_r2 * (t_rI4_xg[t_aI4_j] - t_rI4_cur_x[t_aI4_j]);
			t_rI4_new_v[t_aI4_j] = new Double(Math.ceil(t_aI8_temp_v)).intValue();
			if(t_rI4_new_v[t_aI4_j] > m_aI4_vmax){
				t_rI4_new_v[t_aI4_j] = m_aI4_vmax;
			}
			else if(t_rI4_new_v[t_aI4_j] < m_aI4_vmin){
				t_rI4_new_v[t_aI4_j] = m_aI4_vmin;
			}
			t_rI4_new_x[t_aI4_j] = t_rI4_cur_x[t_aI4_j] + t_rI4_new_v[t_aI4_j];
			if(t_rI4_new_x[t_aI4_j] > m_aI4_xmax){
				t_rI4_new_x[t_aI4_j] = m_aI4_xmax;
			}
		}
		f_aTC_solution.setM_rI4_x(t_rI4_new_x);
		f_aTC_solution.setM_rI4_v(t_rI4_new_v);
		f_aTC_solution.setM_cur_iter(m_aI4_cur_iter);
		f_aTC_solution.setM_cur_nfe(m_aI4_cur_nfe);
	}
	


	@Override
	protected void evolution() {
		while(m_aI4_cur_nfe < m_aI4_max_nfe){
			//System.out.println("m_aI4_cur_nfe = "+m_aI4_cur_nfe+",m_aI4_cur_iter = "+m_aI4_cur_iter);
			for(int t_aI4_i=0;t_aI4_i<m_aI4_size;t_aI4_i++){
				
				ParticleSolution t_aTC_solution = (ParticleSolution) m_aTC_population[t_aI4_i];
				updateXV(t_aTC_solution, t_aI4_i);
				
//				System.out.println("����ǰ��");
//				printSolution(t_aTC_solution);
				modify(t_aTC_solution);//����
//				System.out.println("������");
//				printSolution(t_aTC_solution);
				evaluate(t_aTC_solution);
				saveFitness(m_aTC_gBest);
				m_aI4_cur_nfe++;//���۴�������
				
				updateBest(t_aI4_i);
			}
			updateW();
			m_aI4_cur_iter++;//������������
			//String t_str_res = printPopulation(m_aTC_population);
			//FileUtils.saveFile(m_str_data_path, m_str_alg_name, "test_"+m_str_file_name, t_str_res);
		}
		System.out.println(m_str_alg_name+" m_aI4_cur_nfe = "+m_aI4_cur_nfe);
		System.out.println(m_str_alg_name+" m_aI4_cur_iter = "+m_aI4_cur_iter);
		
	}

	
	@Override
	protected ASolution solve(int f_aI4_p) {
		//��ʼ��
		{
			init();
			initPopulation(NameSpace.s_str_pso);
			initV();
			initBest();
		}
		
		//��ӡ���������ڲ��Ե���
		/*{
			System.out.println("==========��ʼ����Ⱥ==========");
			printPopulation(m_aTC_population);
			System.out.println("==========��ʷ������Ⱥ==========");
			printPopulation(m_aTC_pBest);
			System.out.println("==========ȫ����������==========");
			printSolution(m_aTC_gBest);
		}*/
		
		//��Ⱥ����
		{
			evolution();
		}
		
		//����ı���
		{
			String t_str_result_content = getResultContent(f_aI4_p, m_aTC_gBest);
			FileUtils.saveFile(m_str_data_path, m_str_alg_name, m_str_file_name, t_str_result_content);
		}
		return m_aTC_gBest;
		
	}

	

}
