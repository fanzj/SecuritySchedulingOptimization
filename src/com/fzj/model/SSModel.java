package com.fzj.model;

import java.util.Map;

import com.fzj.config.NameSpace;
import com.fzj.utils.FileUtils;
import com.fzj.utils.MathUtils;

/** 
 * @author Fan Zhengjie 
 * @date 2017年1月28日 下午4:26:32 
 * @version 1.0 
 * @description 安检调度模型
 */
public class SSModel {

	/**
	 * 危险物品种类数
	 */
	private int m_aI4_n;
	
	/**
	 * 安检设备种类数
	 */
	private int m_aI4_m;
	
	/**
	 * 安检场所数
	 */
	private int m_aI4_r;
	
	/**
	 * 问题维度
	 */
	private int m_aI4_d;
	
	/**
	 * 危险物品的权重
	 */
	private double m_rI8_wi[];
	
	/**
	 * 危险物品i在k出现的频率
	 */
	private double m_rI8_fik[][];
	
	/**
	 * 物品i由设备j检测所需的时间
	 */
	private double m_rI8_dtij[][];
	
    /**
     * 危险物品i被设备j检测到的概率
     */
	private double m_rI8_dcij[][];
	
	/**
	 * 安检场所k处的累积损耗时间
	 */
	private double m_rI8_dlk[];
	
	public SSModel(String f_str_path){
		FileUtils.s_aI1_operation = true;//读取路径的选择
		Map<String, Integer> t_aTC_map = FileUtils.readBasicInfo(f_str_path, NameSpace.s_str_basic_info);
		this.m_aI4_n = t_aTC_map.get(NameSpace.s_str_n);
		this.m_aI4_m = t_aTC_map.get(NameSpace.s_str_m);
		this.m_aI4_r = t_aTC_map.get(NameSpace.s_str_r);
		this.m_aI4_d = m_aI4_m * m_aI4_r;
		this.m_rI8_dlk = FileUtils.read1D(f_str_path, NameSpace.s_str_dlk, m_aI4_r);
		this.m_rI8_wi = FileUtils.read1D(f_str_path, NameSpace.s_str_wi, m_aI4_n);
		this.m_rI8_dcij = FileUtils.read2D(f_str_path, NameSpace.s_str_dcij, m_aI4_n, m_aI4_m);
		this.m_rI8_dtij = FileUtils.read2D(f_str_path, NameSpace.s_str_dtij, m_aI4_n, m_aI4_m);
		this.m_rI8_fik = FileUtils.read2D(f_str_path, NameSpace.s_str_fik, m_aI4_n, m_aI4_r);
	}

	public int getM_aI4_n() {
		return m_aI4_n;
	}

	public void setM_aI4_n(int m_aI4_n) {
		this.m_aI4_n = m_aI4_n;
	}

	public int getM_aI4_m() {
		return m_aI4_m;
	}

	public void setM_aI4_m(int m_aI4_m) {
		this.m_aI4_m = m_aI4_m;
	}

	public int getM_aI4_r() {
		return m_aI4_r;
	}

	public void setM_aI4_r(int m_aI4_r) {
		this.m_aI4_r = m_aI4_r;
	}

	public int getM_aI4_d() {
		return m_aI4_d;
	}

	public void setM_aI4_d(int m_aI4_d) {
		this.m_aI4_d = m_aI4_d;
	}

	public double[] getM_rI8_wi() {
		return m_rI8_wi;
	}

	public void setM_rI8_wi(double[] m_rI8_wi) {
		this.m_rI8_wi = m_rI8_wi;
	}

	public double[][] getM_rI8_fik() {
		return m_rI8_fik;
	}

	public void setM_rI8_fik(double[][] m_rI8_fik) {
		this.m_rI8_fik = m_rI8_fik;
	}

	public double[][] getM_rI8_dtij() {
		return m_rI8_dtij;
	}

	public void setM_rI8_dtij(double[][] m_rI8_dtij) {
		this.m_rI8_dtij = m_rI8_dtij;
	}

	public double[][] getM_rI8_dcij() {
		return m_rI8_dcij;
	}

	public void setM_rI8_dcij(double[][] m_rI8_dcij) {
		this.m_rI8_dcij = m_rI8_dcij;
	}

	public double[] getM_rI8_dlk() {
		return m_rI8_dlk;
	}

	public void setM_rI8_dlk(double[] m_rI8_dlk) {
		this.m_rI8_dlk = m_rI8_dlk;
	}
	
	/**
	 * 测试数据是否读取成功
	 * @param args
	 */
	public static void main(String[] args) {
		SSModel t_aTC_ssm = new SSModel(NameSpace.s_str_data_01);
		System.out.println("1.基本信息：");
		System.out.println("n = "+t_aTC_ssm.getM_aI4_n());
		System.out.println("m = "+t_aTC_ssm.getM_aI4_m());
		System.out.println("r = "+t_aTC_ssm.getM_aI4_r());
		System.out.println("d = "+t_aTC_ssm.getM_aI4_d());
		
		System.out.println("2.wi:");
		MathUtils.print1D(t_aTC_ssm.getM_rI8_wi());
		
		System.out.println("3.dlk:");
		MathUtils.print1D(t_aTC_ssm.getM_rI8_dlk());
		
		System.out.println("4.dcij:");
		MathUtils.print2D(t_aTC_ssm.getM_rI8_dcij());
		
		System.out.println("5.dtij:");
		MathUtils.print2D(t_aTC_ssm.getM_rI8_dtij());
		
		System.out.println("6.fik:");
		MathUtils.print2D(t_aTC_ssm.getM_rI8_fik());
		
	}
	

	
}
