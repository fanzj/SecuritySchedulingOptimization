package com.fzj.solution;

/** 
 * @author Fan Zhengjie 
 * @date 2017年2月11日 下午2:23:21 
 * @version 1.0 
 * @description
 */
public class WaveSolution extends ASolution implements Cloneable{

	private int m_aI4_h;// 波高
	private double m_aI8_w;// 波长
	
	private int[] m_rI4_v;//PWWO独有
	
	public WaveSolution() {	
	}
	
	public WaveSolution(int f_aI4_d) {	
		super(f_aI4_d);
	}
	
	public int getM_aI4_h() {
		return m_aI4_h;
	}

	public void setM_aI4_h(int m_aI4_h) {
		this.m_aI4_h = m_aI4_h;
	}

	public double getM_aI8_w() {
		return m_aI8_w;
	}

	public void setM_aI8_w(double m_aI8_w) {
		this.m_aI8_w = m_aI8_w;
	}
	
	public int[] getM_rI4_v() {
		return m_rI4_v;
	}
	
	public void setM_rI4_v(int[] m_rI4_v) {
		this.m_rI4_v = m_rI4_v;
	}

	public WaveSolution clone() {
		WaveSolution t_aTC_wave = null;
		try {
			t_aTC_wave = (WaveSolution) super.clone();
			t_aTC_wave.m_rI4_x = this.m_rI4_x.clone();
			t_aTC_wave.m_rI4_device_j = this.m_rI4_device_j.clone();
			t_aTC_wave.m_rI4_v = this.m_rI4_v.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return t_aTC_wave;
	}
}
