package com.fzj.data;

import java.math.BigDecimal;
import java.util.Random;

import com.fzj.config.NameSpace;
import com.fzj.utils.FileUtils;
import com.fzj.utils.MathUtils;

/**
 * @author dell
 * @date 2017年1月2日 下午9:27:48
 * @version 1.0
 * @Description 随机生成试验数据
 */
public class RandomGenerateData {
	
	/**
	 * 危害物品种类数
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
	 * 随机数生成器
	 */
	private static Random s_aTC_random = new Random(System.currentTimeMillis());
	
	public RandomGenerateData(int f_aI4_n,int f_aI4_m,int f_aI4_r){
		this.m_aI4_n = f_aI4_n;
		this.m_aI4_m = f_aI4_m;
		this.m_aI4_r = f_aI4_r;
	}
	
	/**
	 * 随机生成[f_aI8_low,f_aI8_upper]之间的随机浮点数
	 * @param f_aI8_low
	 * @param f_aI8_upper
	 */
	private static double randDoubleAToB(double f_aI8_low,double f_aI8_upper){
		BigDecimal t_aTC_low = new BigDecimal(f_aI8_low);
		BigDecimal t_aTC_upper = new BigDecimal(f_aI8_upper);
		BigDecimal t_aTC_rand = new BigDecimal(s_aTC_random.nextDouble());
		return t_aTC_low.add((t_aTC_upper.subtract(t_aTC_low)).multiply(t_aTC_rand)).doubleValue();
	}
	
	/**
	 * 生成基本信息
	 * 存放N （危害物品种类数）
	 * 存放M （安检设备种类数）
	 * 存放R （安检场所数）
	 * @param f_str_path
	 */
	private void genBasicInfo(String f_str_path){
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_basic_info, m_aI4_n+","+m_aI4_m+","+m_aI4_r);
	}
	
	/**
	 * 生成危险物品的权重
	 * @param f_str_path
	 */
	private void genWi(String f_str_path){
		String t_str_res = "";
		for(int t_aI4_i=0;t_aI4_i<m_aI4_n;t_aI4_i++){
			t_str_res += MathUtils.formatD8(randDoubleAToB(0, 1));
			if(t_aI4_i < m_aI4_n-1){
				t_str_res += " ";
			}
		}
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_wi, t_str_res);
	}
	
	/**
	 * 生成危险物品i在k出出现的频率
	 * @param f_str_path
	 */
	private void genFik(String f_str_path){
		String t_str_res = "";
		for(int t_aI4_i=0;t_aI4_i<m_aI4_n;t_aI4_i++){
			for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
				t_str_res += MathUtils.formatD8(randDoubleAToB(0, 0.1));
				if(t_aI4_k < m_aI4_r-1){
					t_str_res += " ";
				}else if(t_aI4_i < m_aI4_n-1) {
					t_str_res += "\n";
				}
			}
		}
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_fik, t_str_res);
	}
	
	/**
	 * 物品i由设备j检测所需的时间
	 * @param f_str_path
	 */
	private void genDTij(String f_str_path){
		String t_str_res = "";
		for(int t_aI4_i=0;t_aI4_i<m_aI4_n;t_aI4_i++){
			for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
				t_str_res += MathUtils.formatD8(randDoubleAToB(0, 10));
				if(t_aI4_j < m_aI4_m-1){
					t_str_res += " ";
				}else if(t_aI4_i < m_aI4_n-1) {
					t_str_res += "\n";
				}
			}
		}
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_dtij, t_str_res);
	}

	/**
	 * 危险物品i被设备j检测到的概率
	 * @param f_str_path
	 */
	private void genDCij(String f_str_path){
		String t_str_res = "";
		for(int t_aI4_i=0;t_aI4_i<m_aI4_n;t_aI4_i++){
			for(int t_aI4_j=0;t_aI4_j<m_aI4_m;t_aI4_j++){
				t_str_res += MathUtils.formatD8(randDoubleAToB(0.7, 1));
				if(t_aI4_j < m_aI4_m-1){
					t_str_res += " ";
				}else if(t_aI4_i < m_aI4_n-1){
					t_str_res += "\n";
				}
			}
		}
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_dcij, t_str_res);
	}

	/**
	 * 安检场所k处的累积损耗时间
	 * @param f_str_path
	 */
	private void genDLk(String f_str_path){
		String t_str_res = "";
		for(int t_aI4_k=0;t_aI4_k<m_aI4_r;t_aI4_k++){
			t_str_res += MathUtils.formatD8(randDoubleAToB(5, 10));
			if(t_aI4_k < m_aI4_r-1){
				t_str_res += " ";
			}
		}
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_dlk, t_str_res);
	}


	
	/**
	 * 随机生成试验数据
	 * @param f_str_path 路径
	 */
	private void genExpData(String f_str_path){
		FileUtils.s_aI1_operation = true;//表示当前环境为生成实验数据的路径
		genBasicInfo(f_str_path);
		genWi(f_str_path);
		genFik(f_str_path);
		genDTij(f_str_path);
		genDCij(f_str_path);
		genDLk(f_str_path);
	}
	
	public static void main(String[] args) {
		RandomGenerateData t_aTC_genData = new RandomGenerateData(107, 31, 65);
		t_aTC_genData.genExpData("data_05");
		System.out.println("数据生成成功！");
	}
}
