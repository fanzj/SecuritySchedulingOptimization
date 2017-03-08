package com.fzj.alg.thread;

import com.fzj.config.NameSpace;

/** 
 * @author Fan Zhengjie 
 * @date 2017年2月3日 下午6:21:02 
 * @version 1.0 
 * @description
 */
public class RunThread {

	public static void main(String[] args) {
		int t_aI4_size = 50;
	    int t_aI4_max_nfe = 400000;
		int t_aI4_max_iter = 8000;
		int t_aI4_run_times = 50;
		String t_str_data_path = NameSpace.s_str_data_05;
		
//		Thread t_aTC_alg_thread = new AlgThread(NameSpace.s_str_dednspso,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
//		t_aTC_alg_thread.start();
		
		Thread t_aTC_alg_thread = new AlgThread(NameSpace.s_str_wwo,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
		t_aTC_alg_thread.start();
		
//		Thread t_aTC_fade_thread = new AlgThread(NameSpace.s_str_fade,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
//		t_aTC_fade_thread.start();
		
	/*	Thread t_aTC_pso_thread = new AlgThread(NameSpace.s_str_pso,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
		t_aTC_pso_thread.start();
		
		Thread t_aTC_dnspso_thread = new AlgThread(NameSpace.s_str_dnspso,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
		t_aTC_dnspso_thread.start();
		
		Thread t_aTC_fade_thread = new AlgThread(NameSpace.s_str_fade,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
		t_aTC_fade_thread.start();
		
		Thread t_aTC_wwo_thread = new AlgThread(NameSpace.s_str_wwo,t_aI4_size, t_aI4_max_nfe, t_aI4_max_iter, t_aI4_run_times,t_str_data_path);
		t_aTC_wwo_thread.start();*/
		
	}
}
