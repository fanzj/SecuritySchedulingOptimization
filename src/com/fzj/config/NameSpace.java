package com.fzj.config;
/** 
 * @author Fan Zhengjie 
 * @date 2017年1月28日 下午4:05:00 
 * @version 1.0 
 * @description 命名空间（规范）
 */
public class NameSpace {

	/**
	 * 读取路径
	 */
	public static final String s_str_experiment_data_path = "experiment_data\\";
	
	/**
	 * 写入路径
	 */
	public static final String s_str_experiment_result_path = "experiment_result\\";

	/**
	 * 基本信息文件名 
	 * 存放N （危害物品种类数）
	 * 存放M （安检设备种类数）
	 * 存放R （安检场所数）
	 */
	public static final String s_str_basic_info = "basic_info.txt";
	
	/**
	 * 危险物品的权重
	 */
	public static final String s_str_wi = "wi.txt";
	
	/**
	 * 危险物品i在k出出现的频率
	 */
	public static final String s_str_fik = "fik.txt";
	
	/**
	 * 物品i由设备j检测所需的时间
	 */
	public static final String s_str_dtij = "dtij.txt";
	
	/**
	 * 危险物品i被设备j检测到的概率
	 */
	public static final String s_str_dcij = "dcij.txt";
	
	/**
	 * 安检场所k处的累积损耗时间
	 */
	public static final String s_str_dlk = "dlk.txt";
	
	/**
	 * 第一组实验数据
	 */
	public static final String s_str_data_01 = "data_01\\";
	
	/**
	 * 第二组实验数据
	 */
	public static final String s_str_data_02 = "data_02\\";
	
	/**
	 * 第三组实验数据
	 */
	public static final String s_str_data_03 = "data_03\\";
	
	/**
	 * 第四组实验数据
	 */
	public static final String s_str_data_04 = "data_04\\";
	
	/**
	 * 第五组实验数据
	 */
	public static final String s_str_data_05 = "data_05\\";
	
	/**
	 * 危险物品种类数
	 */
	public static final String s_str_n = "HAZARDOUS_SUBSTANCE";
	
	/**
	 * 安检设备种类数
	 */
	public static final String s_str_m = "SECURITY_DEVICE";
	
	/**
	 * 安检场所数
	 */
	public static final String s_str_r = "SECURITY_CHECKPOINT";
	
	/**
	 * PSO算法
	 */
	public static final String s_str_pso = "PSO";
	
	/**
	 * DNSPSO算法
	 */
	public static final String s_str_dnspso = "DNSPSO";
	
	/**
	 * FADE算法
	 */
	public static final String s_str_fade = "FADE";
	
	/**
	 * WWO算法
	 */
	public static final String s_str_wwo = "WWO";
	
	/**
	 * PWWO算法
	 */
	public static final String s_str_pwwo = "PWWO";
	
	/**
	 * 改进的DNSPSO
	 */
	public static final String s_str_dednspso = "DE_DNSPSO";
	
	/**
	 * txt文件
	 */
	public static final String s_str_file_txt = ".txt";
	
	/**
	 * excel文件
	 */
	public static final String s_str_file_excel = ".xlsx";
	
}
