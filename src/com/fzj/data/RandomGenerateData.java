package com.fzj.data;

import java.math.BigDecimal;
import java.util.Random;

import com.fzj.config.NameSpace;
import com.fzj.utils.FileUtils;
import com.fzj.utils.MathUtils;

/**
 * @author dell
 * @date 2017��1��2�� ����9:27:48
 * @version 1.0
 * @Description ���������������
 */
public class RandomGenerateData {
	
	/**
	 * Σ����Ʒ������
	 */
	private int m_aI4_n;
	
	/**
	 * �����豸������
	 */
	private int m_aI4_m;
	
	/**
	 * ���쳡����
	 */
	private int m_aI4_r;
	
	/**
	 * �����������
	 */
	private static Random s_aTC_random = new Random(System.currentTimeMillis());
	
	public RandomGenerateData(int f_aI4_n,int f_aI4_m,int f_aI4_r){
		this.m_aI4_n = f_aI4_n;
		this.m_aI4_m = f_aI4_m;
		this.m_aI4_r = f_aI4_r;
	}
	
	/**
	 * �������[f_aI8_low,f_aI8_upper]֮������������
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
	 * ���ɻ�����Ϣ
	 * ���N ��Σ����Ʒ��������
	 * ���M �������豸��������
	 * ���R �����쳡������
	 * @param f_str_path
	 */
	private void genBasicInfo(String f_str_path){
		FileUtils.saveFile(f_str_path, "", NameSpace.s_str_basic_info, m_aI4_n+","+m_aI4_m+","+m_aI4_r);
	}
	
	/**
	 * ����Σ����Ʒ��Ȩ��
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
	 * ����Σ����Ʒi��k�����ֵ�Ƶ��
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
	 * ��Ʒi���豸j��������ʱ��
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
	 * Σ����Ʒi���豸j��⵽�ĸ���
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
	 * ���쳡��k�����ۻ����ʱ��
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
	 * ���������������
	 * @param f_str_path ·��
	 */
	private void genExpData(String f_str_path){
		FileUtils.s_aI1_operation = true;//��ʾ��ǰ����Ϊ����ʵ�����ݵ�·��
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
		System.out.println("�������ɳɹ���");
	}
}
