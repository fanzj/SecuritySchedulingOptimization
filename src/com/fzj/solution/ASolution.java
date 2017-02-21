package com.fzj.solution;
/** 
 * @author Fan Zhengjie 
 * @date 2017��1��28�� ����3:06:39 
 * @version 1.0 
 * @description �����
 */
public abstract class ASolution{
	
	protected int m_aI4_d;//ά��
	protected double m_aI8_fitness;//��Ӧ��ֵ
	protected int[] m_rI4_x;//λ����������ʾ�������
	protected int m_cur_iter;//��ǰ��������
	protected int m_cur_nfe;//��ǰ��Ӧ�����۴���
	protected int m_aI4_total_x;//λ�������ܴ�С
	protected int[] m_rI4_device_j;//�����豸������
	
	public ASolution(){}
	
	public ASolution(int f_aI4_d){
		this.m_aI4_d = f_aI4_d;
	}

	public int getM_aI4_d() {
		return m_aI4_d;
	}

	public void setM_aI4_d(int m_aI4_d) {
		this.m_aI4_d = m_aI4_d;
	}

	public double getM_aI8_fitness() {
		return m_aI8_fitness;
	}

	public void setM_aI8_fitness(double m_aI8_fitness) {
		this.m_aI8_fitness = m_aI8_fitness;
	}

	public int[] getM_rI4_x() {
		return m_rI4_x;
	}

	public void setM_rI4_x(int[] m_rI4_x) {
		this.m_rI4_x = m_rI4_x;
	}

	public int getM_cur_iter() {
		return m_cur_iter;
	}

	public void setM_cur_iter(int m_cur_iter) {
		this.m_cur_iter = m_cur_iter;
	}

	public int getM_cur_nfe() {
		return m_cur_nfe;
	}

	public void setM_cur_nfe(int m_cur_nfe) {
		this.m_cur_nfe = m_cur_nfe;
	}
	
	public int getM_aI4_total_x() {
		return m_aI4_total_x;
	}
	
	public void setM_aI4_total_x(int m_aI4_total_x) {
		this.m_aI4_total_x = m_aI4_total_x;
	}
	
	public int[] getM_rI4_device_j() {
		return m_rI4_device_j;
	}
	
	public void setM_rI4_device_j(int[] m_rI4_device_j) {
		this.m_rI4_device_j = m_rI4_device_j;
	}

}
