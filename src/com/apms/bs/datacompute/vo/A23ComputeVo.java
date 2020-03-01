package com.apms.bs.datacompute.vo;

import java.util.Date;

import smartx.framework.common.bs.CommDMO;

import com.apms.ApmsConst;
import com.apms.bs.dfd.DfdVarConst;

/**
 * A23报文计算结果对应，对应数据库A_DFD_A23_COMPUTE表中一条记录
 * @author zzy
 *
 */
public class A23ComputeVo extends DfdComputedVo{
	
	private double ckptt_mid_s13 ;
	private double ckptt_mid_s46 ;
	private double sat_mid_s13 ;
	private double sat_mid_s46 ;
	
	private double pres_mid_s13;
	private double pres_mid_s46;
	private Date datetime_mid_s13;
	private Date datetime_mid_s46 ;
	
	//计算标态气压
    private double nr1 ;
    private double nr2 ;
    private double nr3 ;
    private double nr4 ;
    private double nr5 ;
    private double nr6 ;
    private double nr7 ;
	
	private double pres_st_c15_s13;
	private double pres_st_c15_s46;
	
	private double deta_pres_st = pres_st_c15_s13 - pres_st_c15_s46; //本报文掉压 ST_S46-ST_S13
	
	//本报文S13\S46时间差minutes  DATETIME_MID_S46-DATETIME_MID_S13
	private long  deta_time_min ; 
	
	//本报文掉压率	DETA_PRES_ST/DETA_TIME_MIN*60*24 单位要换算成 PSI/天（24小时）
	private double deta_presrate_st = deta_pres_st/deta_time_min *60*24 ;
	private double deta_presrate_st_roll5 = deta_presrate_st ;
	
	private double deta_pres_fwd = 0; //当前S13标态压力与前一报文标态S46压力差;
	int deta_pres_fwd_s = 0;//当前S13标态压力与前一报文标态S46压力差状态,-1,0,1  表示泄压门限、正常泄压、换瓶
	int pres_s46_warning = 0;//压力告警标识
	
	private double deta_presrate_st_avg         = deta_presrate_st; //本报文掉压率N点均  回滚N点DETA_PRESRATE_ST数据 求均值AVG
	private double deta_presrate_st_std         = 0; //本报文掉压率N点均方 回滚N点DETA_PRESRATE_ST数据 求均值STD
	private int deta_presrate_st_out           = 0; //本报文掉压率DETA超限  超过2个 正负STD的范围 值为1 报警并把该点标记为飘点   在其内设为0 不报警
	private int deta_presrate_st_pointtype     =0;//0-普通点(实点) 1-飘点   2--不良数据连续出现3次时，把这3个飘点改为实点
	private double deta_presrate_st_tsmp_x_avg  = deta_presrate_st; //0~N点的数据均值 
	private double deta_presrate_st_tsmp_y_avg  = deta_presrate_st; //N+1~2N点的数据均值 
	private int deta_presrate_st_tsmp        = 0; //T检验，差异结果1-有差异，0-无差异
	private double deta_presrate_st_tsmp_sign   = 0; //显著性 一般与alpha比较，如果小于alpha那么 这个Diff 结果可信
	private int deta_presrate_st_tsmp_alter  = 0; //值为1时，发告警
	
	private int deta_presrate_st_pointtype_1    =0;//前第一个点
	private int deta_presrate_st_pointtype_2    =0;//前第二个点
	
	private int deta_presrate_st_s46h24_ptype_1 =0;
	public double getPres_st_c15_s13() {
		return pres_st_c15_s13;
	}
	public void setPres_st_c15_s13(double pres_st_c15_s13) {
		this.pres_st_c15_s13 = pres_st_c15_s13;
	}
	public double getPres_st_c15_s46() {
		return pres_st_c15_s46;
	}
	public void setPres_st_c15_s46(double pres_st_c15_s46) {
		this.pres_st_c15_s46 = pres_st_c15_s46;
	}
	public int getDeta_presrate_st_s46h24_ptype_1() {
		return deta_presrate_st_s46h24_ptype_1;
	}
	public void setDeta_presrate_st_s46h24_ptype_1(int deta_presrate_st_s46h24_ptype_1) {
		this.deta_presrate_st_s46h24_ptype_1 = deta_presrate_st_s46h24_ptype_1;
	}
	public int getDeta_presrate_st_s46h24_ptype_2() {
		return deta_presrate_st_s46h24_ptype_2;
	}
	public void setDeta_presrate_st_s46h24_ptype_2(int deta_presrate_st_s46h24_ptype_2) {
		this.deta_presrate_st_s46h24_ptype_2 = deta_presrate_st_s46h24_ptype_2;
	}
	public boolean isDeta_presrate_st_pointtype_alart() {
		return deta_presrate_st_pointtype_alart;
	}
	public void setDeta_presrate_st_pointtype_alart(boolean deta_presrate_st_pointtype_alart) {
		this.deta_presrate_st_pointtype_alart = deta_presrate_st_pointtype_alart;
	}

	private int deta_presrate_st_s46h24_ptype_2 =0;
	
	private String msgno_1 ="0";//前第一个点报文编号
	private String msgno_2 ="0";//前第二个点报文编号
	
	private double  deta_pres_sts46h24;  //回滚到24小时前的最近的一条报文的S46标态压力与本报文的S46标态压力的差值               
	private long	deta_time_mins46h24;              
	private double	deta_presrate_sts46h24;           
	private double	deta_presrate_sts46h24_roll5;         
	private double	deta_presrate_st_avgs46h24;  
	private double	deta_presrate_st_stds46h24;           
	private int	deta_presrate_st_outs46h24;           
	private int	deta_presrate_st_s46h24_ptype;    
	private double	deta_presrate_h24_tsmp_x_avg;
	private double	deta_presrate_h24_tsmp_y_avg;
	private int	deta_presrate_h24_tsmp;
	private double  deta_presrate_h24_tsmp_sign;
	private int	deta_presrate_h24_tsmp_alter;
	
	private double pres_st_c15_s13_roll5;//S13折算压力15度 5点平均
	private double pres_st_c15_s46_roll5;//S46折算压力15度 5点平均
	private double deta_pres_fwd_roll5;//当前S13标态压力与前一报文标态S46压力差 5点平均
	
	private double pres_st_s46_k              ; //"S46基于时间N点斜率 PolyFit_arry  轴为时间，Y轴为S46压力值        
	private double pres_st_s46_b              ; //S46基于时间N点截距 PolyFit_S, y=kx+b 中的b                                      
	private double pres_st_s46_k_avg          ; //S46的K基于时间N点均  PRES_ST_S46_K平均                                          
	private double pres_st_s46_k_std      =0    ; //S46的K基于时间N点均方  PRES_ST_S46_K STD标准方差                                
	private int pres_st_s46_k_out         =0 ; //S46的K基于时间N点STD超限,超限1，不超限-0 
	private int pres_st_s46_k_pointtype =0;//点类型
	private double pres_st_s46_k_tsmp_x_avg   ; //0~N点的数据均值 PRES_ST_S46_K
	private double pres_st_s46_k_tsmp_y_avg   ; //N+1~2N点的数据均值 PRES_ST_S46_K                                                                     
	private int pres_st_s46_k_tsmp         ; //S46的K基于时间2N点独立样本T超限   
	private double pres_st_s46_k_tsmp_sign = 0;
	private int pres_st_s46_k_tsmp_alter   ; //超限告警                                                                 
	private int pres_est_time              ; //预估到达门限时间MINUTES                            
	private Date pres_estlimit_date ;//预计到达压力下限的时间   
	
	private int pres_st_s46_k_pointtype_1    =0;//前第一个点
	private int pres_st_s46_k_pointtype_2    =0;//前第二个点
	
	
	public int getPres_st_s46_k_pointtype() {
		return pres_st_s46_k_pointtype;
	}
	public void setPres_st_s46_k_pointtype(int pres_st_s46_k_pointtype) {
		this.pres_st_s46_k_pointtype = pres_st_s46_k_pointtype;
	}
	public int getPres_st_s46_k_pointtype_1() {
		return pres_st_s46_k_pointtype_1;
	}
	public void setPres_st_s46_k_pointtype_1(int pres_st_s46_k_pointtype_1) {
		this.pres_st_s46_k_pointtype_1 = pres_st_s46_k_pointtype_1;
	}
	public int getPres_st_s46_k_pointtype_2() {
		return pres_st_s46_k_pointtype_2;
	}
	public void setPres_st_s46_k_pointtype_2(int pres_st_s46_k_pointtype_2) {
		this.pres_st_s46_k_pointtype_2 = pres_st_s46_k_pointtype_2;
	}
	public double getPres_st_s46_k_tsmp_sign() {
		return pres_st_s46_k_tsmp_sign;
	}
	public void setPres_st_s46_k_tsmp_sign(double pres_st_s46_k_tsmp_sign) {
		this.pres_st_s46_k_tsmp_sign = pres_st_s46_k_tsmp_sign;
	}

	public Date getPres_estlimit_date() {
		return pres_estlimit_date;
	}
	public void setPres_estlimit_date(Date pres_estlimit_date) {
		this.pres_estlimit_date = pres_estlimit_date;
	}
	public double getPres_st_c15_s13_roll5() {
		return pres_st_c15_s13_roll5;
	}
	public void setPres_st_c15_s13_roll5(double pres_st_c15_s13_roll5) {
		this.pres_st_c15_s13_roll5 = pres_st_c15_s13_roll5;
	}
	public double getPres_st_c15_s46_roll5() {
		return pres_st_c15_s46_roll5;
	}
	public void setPres_st_c15_s46_roll5(double pres_st_c15_s46_roll5) {
		this.pres_st_c15_s46_roll5 = pres_st_c15_s46_roll5;
	}
	public double getDeta_pres_fwd_roll5() {
		return deta_pres_fwd_roll5;
	}
	public void setDeta_pres_fwd_roll5(double deta_pres_fwd_roll5) {
		this.deta_pres_fwd_roll5 = deta_pres_fwd_roll5;
	}
	
	public double getDeta_presrate_st_roll5() {
		return deta_presrate_st_roll5;
	}
	public void setDeta_presrate_st_roll5(double deta_presrate_st_roll5) {
		this.deta_presrate_st_roll5 = deta_presrate_st_roll5;
	}
	public double getPres_st_s46_k() {
		return pres_st_s46_k;
	}
	public void setPres_st_s46_k(double pres_st_s46_k) {
		this.pres_st_s46_k = pres_st_s46_k;
	}
	public double getPres_st_s46_b() {
		return pres_st_s46_b;
	}
	public void setPres_st_s46_b(double pres_st_s46_b) {
		this.pres_st_s46_b = pres_st_s46_b;
	}
	public double getPres_st_s46_k_avg() {
		return pres_st_s46_k_avg;
	}
	public void setPres_st_s46_k_avg(double pres_st_s46_k_avg) {
		this.pres_st_s46_k_avg = pres_st_s46_k_avg;
	}
	public double getPres_st_s46_k_std() {
		return pres_st_s46_k_std;
	}
	public void setPres_st_s46_k_std(double pres_st_s46_k_std) {
		this.pres_st_s46_k_std = pres_st_s46_k_std;
	}
	public int getPres_st_s46_k_out() {
		return pres_st_s46_k_out;
	}
	public void setPres_st_s46_k_out(int pres_st_s46_k_out) {
		this.pres_st_s46_k_out = pres_st_s46_k_out;
	}
	public double getPres_st_s46_k_tsmp_x_avg() {
		return pres_st_s46_k_tsmp_x_avg;
	}
	public void setPres_st_s46_k_tsmp_x_avg(double pres_st_s46_k_tsmp_x_avg) {
		this.pres_st_s46_k_tsmp_x_avg = pres_st_s46_k_tsmp_x_avg;
	}
	public double getPres_st_s46_k_tsmp_y_avg() {
		return pres_st_s46_k_tsmp_y_avg;
	}
	public void setPres_st_s46_k_tsmp_y_avg(double pres_st_s46_k_tsmp_y_avg) {
		this.pres_st_s46_k_tsmp_y_avg = pres_st_s46_k_tsmp_y_avg;
	}
	public int getPres_st_s46_k_tsmp() {
		return pres_st_s46_k_tsmp;
	}
	public void setPres_st_s46_k_tsmp(int pres_st_s46_k_tsmp) {
		this.pres_st_s46_k_tsmp = pres_st_s46_k_tsmp;
	}
	public int getPres_st_s46_k_tsmp_alter() {
		return pres_st_s46_k_tsmp_alter;
	}
	public void setPres_st_s46_k_tsmp_alter(int pres_st_s46_k_tsmp_alter) {
		this.pres_st_s46_k_tsmp_alter = pres_st_s46_k_tsmp_alter;
	}
	public int getPres_est_time() {
		return pres_est_time;
	}
	public void setPres_est_time(int pres_est_time) {
		this.pres_est_time = pres_est_time;
	}
	public int getDeta_presrate_st_pointtype_1() {
		return deta_presrate_st_pointtype_1;
	}
	public void setDeta_presrate_st_pointtype_1(int deta_presrate_st_pointtype_1) {
		this.deta_presrate_st_pointtype_1 = deta_presrate_st_pointtype_1;
	}
	public int getDeta_presrate_st_pointtype_2() {
		return deta_presrate_st_pointtype_2;
	}
	public void setDeta_presrate_st_pointtype_2(int deta_presrate_st_pointtype_2) {
		this.deta_presrate_st_pointtype_2 = deta_presrate_st_pointtype_2;
	}
	
	public double getCkptt_mid_s13() {
		return ckptt_mid_s13;
	}
	public void setCkptt_mid_s13(double ckptt_mid_s13) {
		this.ckptt_mid_s13 = ckptt_mid_s13;
	}
	public double getCkptt_mid_s46() {
		return ckptt_mid_s46;
	}
	public void setCkptt_mid_s46(double ckptt_mid_s46) {
		this.ckptt_mid_s46 = ckptt_mid_s46;
	}
	public double getSat_mid_s13() {
		return sat_mid_s13;
	}
	public void setSat_mid_s13(double sat_mid_s13) {
		this.sat_mid_s13 = sat_mid_s13;
	}
	public double getSat_mid_s46() {
		return sat_mid_s46;
	}
	public void setSat_mid_s46(double sat_mid_s46) {
		this.sat_mid_s46 = sat_mid_s46;
	}
	public double getPres_mid_s13() {
		return pres_mid_s13;
	}
	public void setPres_mid_s13(double pres_mid_s13) {
		this.pres_mid_s13 = pres_mid_s13;
	}
	public double getPres_mid_s46() {
		return pres_mid_s46;
	}
	public void setPres_mid_s46(double pres_mid_s46) {
		this.pres_mid_s46 = pres_mid_s46;
	}
	public Date getDatetime_mid_s13() {
		return datetime_mid_s13;
	}
	public void setDatetime_mid_s13(Date datetime_mid_s13) {
		this.datetime_mid_s13 = datetime_mid_s13;
	}
	public Date getDatetime_mid_s46() {
		return datetime_mid_s46;
	}
	public void setDatetime_mid_s46(Date datetime_mid_s46) {
		this.datetime_mid_s46 = datetime_mid_s46;
	}
	public double getNr1() {
		return nr1;
	}
	public void setNr1(double nr1) {
		this.nr1 = nr1;
	}
	public double getNr2() {
		return nr2;
	}
	public void setNr2(double nr2) {
		this.nr2 = nr2;
	}
	public double getNr3() {
		return nr3;
	}
	public void setNr3(double nr3) {
		this.nr3 = nr3;
	}
	public double getNr4() {
		return nr4;
	}
	public void setNr4(double nr4) {
		this.nr4 = nr4;
	}
	public double getNr5() {
		return nr5;
	}
	public void setNr5(double nr5) {
		this.nr5 = nr5;
	}
	public double getNr6() {
		return nr6;
	}
	public void setNr6(double nr6) {
		this.nr6 = nr6;
	}
	public double getNr7() {
		return nr7;
	}
	public void setNr7(double nr7) {
		this.nr7 = nr7;
	}

	public double getDeta_pres_st() {
		return deta_pres_st;
	}
	public void setDeta_pres_st(double deta_pres_st) {
		this.deta_pres_st = deta_pres_st;
	}
	public long getDeta_time_min() {
		return deta_time_min;
	}
	public void setDeta_time_min(long deta_time_min) {
		this.deta_time_min = deta_time_min;
	}
	public double getDeta_presrate_st() {
		return deta_presrate_st;
	}
	public void setDeta_presrate_st(double deta_presrate_st) {
		this.deta_presrate_st = deta_presrate_st;
	}
	public double getDeta_pres_fwd() {
		return deta_pres_fwd;
	}
	public void setDeta_pres_fwd(double deta_pres_fwd) {
		this.deta_pres_fwd = deta_pres_fwd;
	}
	public int getDeta_pres_fwd_s() {
		return deta_pres_fwd_s;
	}
	public void setDeta_pres_fwd_s(int deta_pres_fwd_s) {
		this.deta_pres_fwd_s = deta_pres_fwd_s;
	}
	public int getPres_s46_warning() {
		return pres_s46_warning;
	}
	public void setPres_s46_warning(int pres_s46_warning) {
		this.pres_s46_warning = pres_s46_warning;
	}
	public double getDeta_presrate_st_avg() {
		return deta_presrate_st_avg;
	}
	public void setDeta_presrate_st_avg(double deta_presrate_st_avg) {
		this.deta_presrate_st_avg = deta_presrate_st_avg;
	}
	public double getDeta_presrate_st_std() {
		return deta_presrate_st_std;
	}
	public void setDeta_presrate_st_std(double deta_presrate_st_std) {
		this.deta_presrate_st_std = deta_presrate_st_std;
	}
	public int getDeta_presrate_st_out() {
		return deta_presrate_st_out;
	}
	public void setDeta_presrate_st_out(int deta_presrate_st_out) {
		this.deta_presrate_st_out = deta_presrate_st_out;
	}
	public int getDeta_presrate_st_pointtype() {
		return deta_presrate_st_pointtype;
	}
	public void setDeta_presrate_st_pointtype(int deta_presrate_st_pointtype) {
		this.deta_presrate_st_pointtype = deta_presrate_st_pointtype;
	}
	public double getDeta_presrate_st_tsmp_x_avg() {
		return deta_presrate_st_tsmp_x_avg;
	}
	public void setDeta_presrate_st_tsmp_x_avg(double deta_presrate_st_tsmp_x_avg) {
		this.deta_presrate_st_tsmp_x_avg = deta_presrate_st_tsmp_x_avg;
	}
	public double getDeta_presrate_st_tsmp_y_avg() {
		return deta_presrate_st_tsmp_y_avg;
	}
	public void setDeta_presrate_st_tsmp_y_avg(double deta_presrate_st_tsmp_y_avg) {
		this.deta_presrate_st_tsmp_y_avg = deta_presrate_st_tsmp_y_avg;
	}
	public int getDeta_presrate_st_tsmp() {
		return deta_presrate_st_tsmp;
	}
	public void setDeta_presrate_st_tsmp(int deta_presrate_st_tsmp) {
		this.deta_presrate_st_tsmp = deta_presrate_st_tsmp;
	}
	public double getDeta_presrate_st_tsmp_sign() {
		return deta_presrate_st_tsmp_sign;
	}
	public void setDeta_presrate_st_tsmp_sign(double deta_presrate_st_tsmp_sign) {
		this.deta_presrate_st_tsmp_sign = deta_presrate_st_tsmp_sign;
	}
	public int getDeta_presrate_st_tsmp_alter() {
		return deta_presrate_st_tsmp_alter;
	}
	public void setDeta_presrate_st_tsmp_alter(int deta_presrate_st_tsmp_alter) {
		this.deta_presrate_st_tsmp_alter = deta_presrate_st_tsmp_alter;
	}


	public double getDeta_pres_sts46h24() {
		return deta_pres_sts46h24;
	}
	public void setDeta_pres_sts46h24(double deta_pres_sts46h24) {
		this.deta_pres_sts46h24 = deta_pres_sts46h24;
	}
	public long getDeta_time_mins46h24() {
		return deta_time_mins46h24;
	}
	public void setDeta_time_mins46h24(long deta_time_mins46h24) {
		this.deta_time_mins46h24 = deta_time_mins46h24;
	}
	public double getDeta_presrate_sts46h24() {
		return deta_presrate_sts46h24;
	}
	public void setDeta_presrate_sts46h24(double deta_presrate_sts46h24) {
		this.deta_presrate_sts46h24 = deta_presrate_sts46h24;
	}
	public double getDeta_presrate_sts46h24_roll5() {
		return deta_presrate_sts46h24_roll5;
	}
	public void setDeta_presrate_sts46h24_roll5(double deta_presrate_sts46h24_roll5) {
		this.deta_presrate_sts46h24_roll5 = deta_presrate_sts46h24_roll5;
	}
	public double getDeta_presrate_st_avgs46h24() {
		return deta_presrate_st_avgs46h24;
	}
	public void setDeta_presrate_st_avgs46h24(double deta_presrate_st_avgs46h24) {
		this.deta_presrate_st_avgs46h24 = deta_presrate_st_avgs46h24;
	}
	public double getDeta_presrate_st_stds46h24() {
		return deta_presrate_st_stds46h24;
	}
	public void setDeta_presrate_st_stds46h24(double deta_presrate_st_stds46h24) {
		this.deta_presrate_st_stds46h24 = deta_presrate_st_stds46h24;
	}
	public int getDeta_presrate_st_outs46h24() {
		return deta_presrate_st_outs46h24;
	}
	public void setDeta_presrate_st_outs46h24(int deta_presrate_st_outs46h24) {
		this.deta_presrate_st_outs46h24 = deta_presrate_st_outs46h24;
	}
	public int getDeta_presrate_st_s46h24_ptype() {
		return deta_presrate_st_s46h24_ptype;
	}
	public void setDeta_presrate_st_s46h24_ptype(int deta_presrate_st_s46h24_ptype) {
		this.deta_presrate_st_s46h24_ptype = deta_presrate_st_s46h24_ptype;
	}
	public double getDeta_presrate_h24_tsmp_x_avg() {
		return deta_presrate_h24_tsmp_x_avg;
	}
	public void setDeta_presrate_h24_tsmp_x_avg(double deta_presrate_h24_tsmp_x_avg) {
		this.deta_presrate_h24_tsmp_x_avg = deta_presrate_h24_tsmp_x_avg;
	}
	public double getDeta_presrate_h24_tsmp_y_avg() {
		return deta_presrate_h24_tsmp_y_avg;
	}
	public void setDeta_presrate_h24_tsmp_y_avg(double deta_presrate_h24_tsmp_y_avg) {
		this.deta_presrate_h24_tsmp_y_avg = deta_presrate_h24_tsmp_y_avg;
	}
	public int getDeta_presrate_h24_tsmp() {
		return deta_presrate_h24_tsmp;
	}
	public void setDeta_presrate_h24_tsmp(int deta_presrate_h24_tsmp) {
		this.deta_presrate_h24_tsmp = deta_presrate_h24_tsmp;
	}
	public double getDeta_presrate_h24_tsmp_sign() {
		return deta_presrate_h24_tsmp_sign;
	}
	public void setDeta_presrate_h24_tsmp_sign(double deta_presrate_h24_tsmp_sign) {
		this.deta_presrate_h24_tsmp_sign = deta_presrate_h24_tsmp_sign;
	}
	public int getDeta_presrate_h24_tsmp_alter() {
		return deta_presrate_h24_tsmp_alter;
	}
	public void setDeta_presrate_h24_tsmp_alter(int deta_presrate_h24_tsmp_alter) {
		this.deta_presrate_h24_tsmp_alter = deta_presrate_h24_tsmp_alter;
	}
	
	
	
	public String getMsgno_1() {
		return msgno_1;
	}
	public void setMsgno_1(String msgno_1) {
		this.msgno_1 = msgno_1;
	}
	public String getMsgno_2() {
		return msgno_2;
	}
	public void setMsgno_2(String msgno_2) {
		this.msgno_2 = msgno_2;
	}
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		//计算后数据插入！！！！！！！！
		String insertSql = "insert into a_dfd_a23_compute(RECORDTIME,id,msg_no,acnum,date_utc "
			+ " ,ckptt_mid_s13,sat_mid_s13,pres_mid_s13,pres_st_c15_s13,pres_st_c15_s13_roll5,datetime_mid_s13 "
			+ " ,ckptt_mid_s46,sat_mid_s46,pres_mid_s46,pres_st_c15_s46,pres_st_c15_s46_roll5,datetime_mid_s46 "
			+ " ,deta_pres_fwd,deta_pres_fwd_roll5,deta_pres_fwd_s,pres_s46_warning"
			+ " ,deta_pres_st,deta_time_min,deta_presrate_st,deta_presrate_st_roll5 "
			+ " ,deta_presrate_st_avg,deta_presrate_st_std,deta_presrate_st_out,deta_presrate_st_pointtype"
			+ " ,deta_presrate_st_tsmp_x_avg,deta_presrate_st_tsmp_y_avg,deta_presrate_st_tsmp,deta_presrate_st_tsmp_sign,deta_presrate_st_tsmp_alter"
			+ " ,deta_pres_sts46h24,deta_time_mins46h24,deta_presrate_sts46h24,deta_presrate_sts46h24_roll5"
			+ " ,deta_presrate_st_avgs46h24,deta_presrate_st_stds46h24,deta_presrate_st_outs46h24,deta_presrate_st_s46h24_ptype"
			+ " ,deta_presrate_h24_tsmp_x_avg,deta_presrate_h24_tsmp_y_avg,deta_presrate_h24_tsmp,deta_presrate_h24_tsmp_sign,deta_presrate_h24_tsmp_alter"
			+ " ,pres_st_s46_k,pres_st_s46_b,pres_st_s46_k_avg,pres_st_s46_k_std,pres_st_s46_k_out,pres_st_s46_k_pointtype"
			+ " ,pres_st_s46_k_tsmp_x_avg,pres_st_s46_k_tsmp_y_avg,pres_st_s46_k_tsmp,pres_st_s46_k_tsmp_sign,pres_st_s46_k_tsmp_alter"
			+ " ,pres_estlimit_date,eventtype"
			+ " ) values(sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,0)";
		
		

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, insertSql,id,msgno,acnum,date_utc
				,ckptt_mid_s13,sat_mid_s13,pres_mid_s13,pres_st_c15_s13,pres_st_c15_s13_roll5,datetime_mid_s13
				,ckptt_mid_s46,sat_mid_s46,pres_mid_s46,pres_st_c15_s46,pres_st_c15_s46_roll5,datetime_mid_s46
				,deta_pres_fwd,deta_pres_fwd_roll5,deta_pres_fwd_s,pres_s46_warning
				,deta_pres_st,deta_time_min,deta_presrate_st,deta_presrate_st_roll5
				,deta_presrate_st_avg,deta_presrate_st_std,deta_presrate_st_out,deta_presrate_st_pointtype
				,deta_presrate_st_tsmp_x_avg,deta_presrate_st_tsmp_y_avg,deta_presrate_st_tsmp,deta_presrate_st_tsmp_sign,deta_presrate_st_tsmp_alter
				,deta_pres_sts46h24,deta_time_mins46h24,deta_presrate_sts46h24,deta_presrate_sts46h24_roll5
				,deta_presrate_st_avgs46h24,deta_presrate_st_stds46h24,deta_presrate_st_outs46h24,deta_presrate_st_s46h24_ptype
				,deta_presrate_h24_tsmp_x_avg,deta_presrate_h24_tsmp_y_avg,deta_presrate_h24_tsmp,deta_presrate_h24_tsmp_sign,deta_presrate_h24_tsmp_alter
				,pres_st_s46_k,pres_st_s46_b,pres_st_s46_k_avg,pres_st_s46_k_std,pres_st_s46_k_out,pres_st_s46_k_pointtype
				,pres_st_s46_k_tsmp_x_avg,pres_st_s46_k_tsmp_y_avg,pres_st_s46_k_tsmp,pres_st_s46_k_tsmp_sign,pres_st_s46_k_tsmp_alter
				,pres_estlimit_date
				);
		
		updatePreviosPointType();
		
		logger.debug("报文["+msgno+"]计算完成并入库！");
	}
	
	boolean deta_presrate_st_pointtype_alart = false;
	
	/**
	 * 更新该点前面的飘点为实点
	 * @throws Exception
	 */
	private void updatePreviosPointType() throws Exception{
		String updateSql = "update a_dfd_a23_compute t set t.deta_presrate_st_pointtype=0 where t.msg_no =? ";
		if(deta_presrate_st_pointtype == DfdVarConst.POINTTYPE_FLOAT && deta_presrate_st_pointtype_1 == DfdVarConst.POINTTYPE_FLOAT
				&& deta_presrate_st_pointtype_2 == DfdVarConst.POINTTYPE_FLOAT){
			//连续3个点为飘点，改为实点
			
			deta_presrate_st_pointtype_alart = true;
			//deta_presrate_st_pointtype = DfdVarConst.POINTTYPE_REAL;
			CommDMO dmo = new CommDMO();
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, msgno_1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, msgno_2);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql, msgno);
		}
		
		String updateSql2 = "update a_dfd_a23_compute t set deta_presrate_st_s46h24_ptype=0 where t.msg_no =? ";
		if(deta_presrate_st_s46h24_ptype == DfdVarConst.POINTTYPE_FLOAT && deta_presrate_st_s46h24_ptype_1 == DfdVarConst.POINTTYPE_FLOAT
				&& deta_presrate_st_s46h24_ptype_2 == DfdVarConst.POINTTYPE_FLOAT){
			//连续3个点为飘点，改为实点
			
			deta_presrate_st_pointtype_alart = true;
			//deta_presrate_st_pointtype = DfdVarConst.POINTTYPE_REAL;
			CommDMO dmo = new CommDMO();
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql2, msgno_1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql2, msgno_2);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql2, msgno);
		}
		
		String updateSql3 = "update a_dfd_a23_compute t set PRES_ST_S46_K_POINTTYPE=0 where t.msg_no =? ";
		if(pres_st_s46_k_pointtype == DfdVarConst.POINTTYPE_FLOAT && pres_st_s46_k_pointtype_1 == DfdVarConst.POINTTYPE_FLOAT
				&& pres_st_s46_k_pointtype_2 == DfdVarConst.POINTTYPE_FLOAT){
			//连续3个点为飘点，改为实点
			
			CommDMO dmo = new CommDMO();
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql3, msgno_1);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql3, msgno_2);
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updateSql3, msgno);
		}
		
	}

}
