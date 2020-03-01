package com.apms.bs.datacompute.vo;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import smartx.framework.common.bs.CommDMO;
import smartx.framework.common.vo.NovaLogger;

import com.apms.ApmsConst;
import com.apms.bs.datacompute.DataComputeCacheService;
import com.apms.bs.dfd.DfdVarConst;

/**
 * 回归数据计算，求均值、方差、样本T检验、斜率、斜率方差、斜率T检验
 * @author jerry
 * @date Mar 26, 2013
 */
public class FieldComputeVo {
	private Logger logger = NovaLogger.getLogger(this.getClass());
	
	private String rptno                   ;//报文类型 
	private String msg_no                  ;//报文编号
	private String acnum                   ;//飞机号
	private String f_name                  ;//计算字段
	private Double f_value                 ;//字段值
	private Double f_value_roll5           = f_value;//字段值，5点均  
	private Double v_avg                   = f_value;//N点均值 
	private Double v_std                   =0d;//N点STD  
	private Integer    v_out                   =0;//N点 2个正负STD的飘点告警
	private Integer    v_pointtype             =0;//点类型0-普通点(实点)  1-飘点, 2--不良数据   
	private boolean v_pointtype_alart     =false; //如果出现连续3个飘点，进行告警
	private Double v_tsmp_x_avg            = f_value;//0~N点的数据均值
	private Double v_tsmp_y_avg            = f_value;//N+1~2N点的数据均值      
	private Integer    v_tsmp                  =0;//数据独立样本T检验超限结果                                 
	private Double v_tsmp_sign             =0d;//独立样本T检验显著性     
	private Integer    v_tsmp_alter            =0;//STA_V1_TSMP=1时 两组数据有明显差异 SIGN在置信区间并且两组数据平均值异常一定百分比，如30% 
	private String x_fieldname             ;//一元回归时，回归变量名 
	private Double x_value                 ;//回归变量值
	private Double v_k                     ;//N点基于另一个字段值（如AHRS ）得到一元回归的 K值          
	private Double yy                      ;
	private Double v_b                     ;//STA_V1基于AHRS 得到一元回归的 B值                         
	private Double v_k_avg                 ;//N点 V_K的均值  
	private Double v_k_std                 =0d;//N点 V_K的标准方差       
	private Integer    v_k_out                 =0;//N点 2个正负STD的飘点告警
	private Integer    v_k_pointtype           =0;//0-普通点(实点)  1-飘点,  2--不良数据  
	private boolean v_k_pointtype_alart     =false; //如果出现连续3个飘点，进行告警
	private Double v_k_tsmp_x_avg          =0d;//0~N点的数据均值
	private Double v_k_tsmp_y_avg         =0d;//N+1~2N点的数据均值      
	private Integer    v_k_tsmp                =0;//独立样本T检验结果
	private Double v_k_tsmp_sign           =0d;//独立样本T检验显著性     
	private Integer    v_k_tsmp_alter          =0;//样本T检验告警  
	private Double x_on_estlimitvalue      =0d;//计算字段到达门限值时，自变量值(如时间)                    
	private Double deta_x_on_estlimitvalue =0d;//计算字段到达门限值时，剩余自变量值(如时间)
	
	//判断前面两个点是否是飘点
	private Integer pre1_v_pointtype=0;
	
	private Integer pre2_v_pointtype=0;
	private String pre1_msgno;
	private String pre2_msgno;
	
	private Integer times =1;//计算回归次数
	private Double limitY;//红线值
	private boolean isEstimateLimit = false;//是否估计及限值
	
	private Double up_value;
	private Double down_value;
	private Integer isOverFlow=0;//是否超出范围默认值
	private boolean isRank = false;//是否计算斜率
	private boolean isRankScope=false;//是否计算斜率区间

	public boolean isRank() {
		return isRank;
	}

	public void setRank(boolean isRank) {
		this.isRank = isRank;
	}

	public Double getYy() {
		return yy;
	}

	public void setYy(Double yy) {
		this.yy = yy;
	}

	public boolean isRankScope() {
		return isRankScope;
	}

	public void setRankScope(boolean isRankScope) {
		this.isRankScope = isRankScope;
	}

	public Double getUp_value() {
		return up_value;
	}

	public void setUp_value(Double up_value) {
		this.up_value = up_value;
	}

	public Double getDown_value() {
		return down_value;
	}

	public void setDown_value(Double down_value) {
		this.down_value = down_value;
	}
	public Integer getIsOverFlow() {
		return isOverFlow;
	}

	public void setIsOverFlow(Integer isOverFlow) {
		this.isOverFlow = isOverFlow;
	}
	
	public FieldComputeVo(String rptno,String msg_no,String acnum,String f_name,Double f_value){
		this.rptno = rptno;
		this.msg_no=msg_no;
		this.acnum = acnum;
		this.f_name = f_name;
		this.f_value = f_value;
		
		f_value_roll5 = f_value;
		v_avg = f_value;
	}
	
	public FieldComputeVo(String rptno,String msg_no,String acnum,String f_name,Double f_value,String x_fieldname,Double x_value){
		this.rptno = rptno;
		this.msg_no=msg_no;
		this.acnum = acnum;
		this.f_name = f_name;
		this.f_value = f_value;
		this.x_fieldname = x_fieldname;
		this.x_value = x_value;
		
		f_value_roll5 = f_value;
		v_avg = f_value;
	}
	
	/**
	 * 插入数据到5点均值表
	 * @throws Exception
	 */
	public void insertToRollNTable() throws Exception{
		CommDMO dmo = new CommDMO();
		
		StringBuilder sb = new StringBuilder("insert into A_DFD_FIELD_NROLL(");
		sb.append("ID,RPTNO,MSG_NO,ACNUM,F_NAME,F_VALUE,F_VALUE_ROLL_N,N_VALUE");
		sb.append(" )");
		sb.append("values(S_A_DFD_FIELD_NROLL.nextval,?,?,?,?,?,?,?)");//7个？

		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString()
				,rptno,msg_no,acnum,f_name,f_value,f_value_roll5,5);
		
		updatePreviosPointType();
		
		//logger.debug("报文["+msg_no+"],["+f_name+"]字段5点滚动平均值数据入库！");
	}
	
	//计算数据入库持久化
	public void insertToTable() throws Exception{
		CommDMO dmo = new CommDMO();
		
		StringBuilder sb = new StringBuilder("insert into A_DFD_FIELD_COMPUTE(");
		sb.append("ID,RPTNO,MSG_NO,ACNUM,F_NAME,F_VALUE,F_VALUE_ROLL5");
		sb.append(",V_AVG,V_STD,V_OUT,V_POINTTYPE");
		sb.append(",V_TSMP_X_AVG,V_TSMP_Y_AVG,V_TSMP,V_TSMP_SIGN,V_TSMP_ALTER");
		sb.append(",X_FIELDNAME,X_VALUE,V_K,V_B,V_K_AVG,V_K_STD,V_K_OUT,V_K_POINTTYPE");
		sb.append(",V_K_TSMP_X_AVG,V_K_TSMP_Y_AVG,V_K_TSMP,V_K_TSMP_SIGN,V_K_TSMP_ALTER");
		sb.append(",X_ON_ESTLIMITVALUE,DETA_X_ON_ESTLIMITVALUE,UP_VALUE,DOWN_VALUE,ISOVERFLOW,YY");
		sb.append(" )");
		sb.append("values(S_A_DFD_FIELD_COMPUTE.nextval,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");//32个？
		
		dmo.executeUpdateByDS(ApmsConst.DS_APMS, sb.toString()
				,rptno,msg_no,acnum,f_name,f_value,f_value_roll5
				,v_avg,v_std,v_out,v_pointtype
				,v_tsmp_x_avg,v_tsmp_y_avg,v_tsmp,v_tsmp_sign,v_tsmp_alter
				,x_fieldname,x_value,v_k,v_b,v_k_avg,v_k_std,v_k_out,v_k_pointtype
				,v_k_tsmp_x_avg,v_k_tsmp_y_avg,v_k_tsmp,v_k_tsmp_sign,v_k_tsmp_alter
				,x_on_estlimitvalue,deta_x_on_estlimitvalue,up_value,down_value,isOverFlow,yy
		 		);
		
		updatePreviosPointType();
		
		//logger.debug("报文["+msg_no+"],["+f_name+"]字段回归计算数据入库！");
	}
	
	/**
	 * 如果是连续3个飘点，更新该点前面的飘点为实点
	 * @throws Exception
	 */
	private void updatePreviosPointType() throws Exception{
		String updatePointType  = "UPDATE A_DFD_FIELD_COMPUTE T SET V_POINTTYPE=0   WHERE T.MSG_NO = ANY(?,?,?) AND F_NAME=?";
		
		//TODO 计算出的K值是否也进行飘点告警计算？
		//String updateKPointType = "UPDATE A_DFD_FIELD_COMPUTE T SET V_K_POINTTYPE=0 WHERE T.MSG_NO = ANY(?,?,?) AND F_NAME=?";
		
		//连续3个点为飘点，改为实点
		if(pre1_v_pointtype == DfdVarConst.POINTTYPE_FLOAT && pre2_v_pointtype == DfdVarConst.POINTTYPE_FLOAT
				&& v_pointtype == DfdVarConst.POINTTYPE_FLOAT){
			
			v_pointtype_alart = true;
			v_pointtype = DfdVarConst.POINTTYPE_REAL;
			
			CommDMO dmo = new CommDMO();
			dmo.executeUpdateByDS(ApmsConst.DS_APMS, updatePointType, pre1_msgno,pre2_msgno,msg_no,f_name);
			
			//修改缓存数据
			ArrayList<FieldComputeVo> dataHis = DataComputeCacheService.getInstance().getFieldComputedVoHis(
					acnum, rptno, f_name, msg_no,"0"); //到这一步，缓存已经不是空值
			
			//刷新缓存中的数据为实点zhangzy 2014-11-7,此时当前节点已加入缓存中
			if(dataHis.size() >= 3){
				FieldComputeVo pre2vo = dataHis.get(2);
				pre2vo.setV_pointtype(0);
			}
			
			if(dataHis.size() >= 2){
				FieldComputeVo pre1vo = dataHis.get(1);
				pre1vo.setV_pointtype(0);
			}
			
			logger.debug("["+rptno+"]报文,飞机号["+acnum+"],msgno=["+msg_no+"],字段["+f_name+"]出现连续3个飘点，修改为实点!");
		}
	}


	
	public String getRptno() {
		return rptno;
	}

	public void setRptno(String rptno) {
		this.rptno = rptno;
	}

	public String getMsg_no() {
		return msg_no;
	}
	public void setMsg_no(String msg_no) {
		this.msg_no = msg_no;
	}
	public String getAcnum() {
		return acnum;
	}
	public void setAcnum(String acnum) {
		this.acnum = acnum;
	}
	public String getF_name() {
		return f_name;
	}
	public void setF_name(String f_name) {
		this.f_name = f_name;
	}
	public Double getF_value() {
		return f_value;
	}
	public void setF_value(Double f_value) {
		this.f_value = f_value;
	}
	public Double getF_value_roll5() {
		return f_value_roll5;
	}
	public void setF_value_roll5(Double f_value_roll5) {
		this.f_value_roll5 = f_value_roll5;
	}
	public Double getV_avg() {
		return v_avg;
	}
	public void setV_avg(Double v_avg) {
		this.v_avg = v_avg;
	}
	public Double getV_std() {
		return v_std;
	}
	public void setV_std(Double v_std) {
		this.v_std = v_std;
	}
	public Integer getV_out() {
		return v_out;
	}
	public void setV_out(Integer v_out) {
		this.v_out = v_out;
	}
	public Integer getV_pointtype() {
		return v_pointtype;
	}
	public void setV_pointtype(Integer v_pointtype) {
		this.v_pointtype = v_pointtype;
	}
	public Double getV_tsmp_x_avg() {
		return v_tsmp_x_avg;
	}
	public void setV_tsmp_x_avg(Double v_tsmp_x_avg) {
		this.v_tsmp_x_avg = v_tsmp_x_avg;
	}
	public Double getV_tsmp_y_avg() {
		return v_tsmp_y_avg;
	}
	public void setV_tsmp_y_avg(Double v_tsmp_y_avg) {
		this.v_tsmp_y_avg = v_tsmp_y_avg;
	}
	public Integer getV_tsmp() {
		return v_tsmp;
	}
	public void setV_tsmp(Integer v_tsmp) {
		this.v_tsmp = v_tsmp;
	}
	public Double getV_tsmp_sign() {
		return v_tsmp_sign;
	}
	public void setV_tsmp_sign(Double v_tsmp_sign) {
		this.v_tsmp_sign = v_tsmp_sign;
	}
	public Integer getV_tsmp_alter() {
		return v_tsmp_alter;
	}
	public void setV_tsmp_alter(Integer v_tsmp_alter) {
		this.v_tsmp_alter = v_tsmp_alter;
	}
	public String getX_fieldname() {
		return x_fieldname;
	}
	public void setX_fieldname(String x_fieldname) {
		this.x_fieldname = x_fieldname;
	}
	
	public Double getX_value() {
		return x_value;
	}

	public void setX_value(Double x_value) {
		this.x_value = x_value;
	}

	public Double getV_k() {
		return v_k;
	}
	public void setV_k(Double v_k) {
		this.v_k = v_k;
	}
	public Double getV_b() {
		return v_b;
	}
	public void setV_b(Double v_b) {
		this.v_b = v_b;
	}
	public Double getV_k_avg() {
		return v_k_avg;
	}
	public void setV_k_avg(Double v_k_avg) {
		this.v_k_avg = v_k_avg;
	}
	public Double getV_k_std() {
		return v_k_std;
	}
	public void setV_k_std(Double v_k_std) {
		this.v_k_std = v_k_std;
	}
	public Integer getV_k_out() {
		return v_k_out;
	}
	public void setV_k_out(Integer v_k_out) {
		this.v_k_out = v_k_out;
	}
	public Integer getV_k_pointtype() {
		return v_k_pointtype;
	}
	public void setV_k_pointtype(Integer v_k_pointtype) {
		this.v_k_pointtype = v_k_pointtype;
	}
	public Double getV_k_tsmp_x_avg() {
		return v_k_tsmp_x_avg;
	}
	public void setV_k_tsmp_x_avg(Double v_k_tsmp_x_avg) {
		this.v_k_tsmp_x_avg = v_k_tsmp_x_avg;
	}
	public Double getV_k_tsmp_y_avg() {
		return v_k_tsmp_y_avg;
	}
	public void setV_k_tsmp_y_avg(Double v_k_tsmp_y_avg) {
		this.v_k_tsmp_y_avg = v_k_tsmp_y_avg;
	}
	public Integer getV_k_tsmp() {
		return v_k_tsmp;
	}
	public void setV_k_tsmp(Integer v_k_tsmp) {
		this.v_k_tsmp = v_k_tsmp;
	}
	public Double getV_k_tsmp_sign() {
		return v_k_tsmp_sign;
	}
	public void setV_k_tsmp_sign(Double v_k_tsmp_sign) {
		this.v_k_tsmp_sign = v_k_tsmp_sign;
	}
	public Integer getV_k_tsmp_alter() {
		return v_k_tsmp_alter;
	}
	public void setV_k_tsmp_alter(Integer v_k_tsmp_alter) {
		this.v_k_tsmp_alter = v_k_tsmp_alter;
	}
	public Double getX_on_estlimitvalue() {
		return x_on_estlimitvalue;
	}
	public void setX_on_estlimitvalue(Double x_on_estlimitvalue) {
		this.x_on_estlimitvalue = x_on_estlimitvalue;
	}
	public Double getDeta_x_on_estlimitvalue() {
		return deta_x_on_estlimitvalue;
	}
	public void setDeta_x_on_estlimitvalue(Double deta_x_on_estlimitvalue) {
		this.deta_x_on_estlimitvalue = deta_x_on_estlimitvalue;
	}

	public Integer getPre1_v_pointtype() {
		return pre1_v_pointtype;
	}

	public void setPre1_v_pointtype(Integer pre1_v_pointtype) {
		this.pre1_v_pointtype = pre1_v_pointtype;
	}

	public Integer getPre2_v_pointtype() {
		return pre2_v_pointtype;
	}

	public void setPre2_v_pointtype(Integer pre2_v_pointtype) {
		this.pre2_v_pointtype = pre2_v_pointtype;
	}

	public String getPre1_msgno() {
		return pre1_msgno;
	}

	public void setPre1_msgno(String pre1_msgno) {
		this.pre1_msgno = pre1_msgno;
	}

	public String getPre2_msgno() {
		return pre2_msgno;
	}

	public void setPre2_msgno(String pre2_msgno) {
		this.pre2_msgno = pre2_msgno;
	}

	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public Double getLimitY() {
		return limitY;
	}

	public void setLimitY(Double limitY) {
		this.limitY = limitY;
	}

	public boolean isEstimateLimit() {
		return isEstimateLimit;
	}

	public void setEstimateLimit(boolean isEstimateLimit) {
		this.isEstimateLimit = isEstimateLimit;
	}

	public boolean isV_pointtype_alart() {
		return v_pointtype_alart;
	}

	public void setV_pointtype_alart(boolean v_pointtype_alart) {
		this.v_pointtype_alart = v_pointtype_alart;
	}

	public boolean isV_k_pointtype_alart() {
		return v_k_pointtype_alart;
	}

	public void setV_k_pointtype_alart(boolean v_k_pointtype_alart) {
		this.v_k_pointtype_alart = v_k_pointtype_alart;
	}
	
}
