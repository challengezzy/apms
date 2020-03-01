package com.apms.flex.vo
{
	[Bindable]
	[RemoteClass(alias="com.apms.bs.apu.vo.A_DFD_A13_COMPUTED")]
	public class A_DFD_A13_COMPUTED
	{
		//红线值
		public var RL_EGT_COR :Number;
		
		public var ID                   :String ; //ID                                            
		public var MSG_NO               :String ; //ACARS报文编号                                 
		public var ACNUM                :String ; //飞机号                                        
		public var UTCDATE              :Date   ; //报文时间                                      
		public var ISCHANGEPOINT        :Number ; //是否是换发点0-否,1-是                         
		public var TAT                  :Number ; //外界温度                                      
		public var ALT                  :Number ; //气压海拔                                      
		public var ASN                  :String ; //APU序号                                       
		public var AHRS                 :Number ; //APU小时(分钟)    
		public var AHRS_DATE            :String;
		
		public var ACYC                 :Number ; //APU循环                                       
		public var PDI_OLD              :Number ; //性能指数                                      
		public var PDI_NEW              :Number ; //性能指数-新                                   
		public var CODE                 :String ; //发送代码                                      
		public var THITA                :Number ; //THITA值                                       
		public var THITA_LCIT           :Number ; //标态大气环境修正系数LCIT                      
		public var ESN_MAX              :String ; //APU最大EGT时的发动机序号                      
		public var NA_MAX               :Number ; //APU最大EGT时的转速                            
		public var EGTA_MAX             :Number ; //APU最大EGT时的排气温度                        
		public var IGV_MAX              :Number ; //APU最大EGT时的IGV角度                         
		public var P2A_MAX              :Number ; //APU最大EGT时的压气机进口压力                  
		public var LCIT_MAX             :Number ; //APU最大EGT时的压气机进口温度                  
		public var WB_MAX               :Number ; //APU最大EGT时的引气流量 kg/sec                 
		public var PT_MAX               :Number ; //APU最大EGT时的引气压力                        
		public var LCDT_MAX             :Number ; //APU最大EGT时的负载压气机出口温度              
		public var OTA_MAX              :Number ; //APU最大EGT时的滑油温度                        
		public var GLA_MAX              :Number ; //APU最大EGT时的发电机负载 %                    
		public var SCV_MAX              :Number ; //APU伺服活门角度 A330 A340                     
		public var HOT_MAX              :Number ; //高位滑油温度 A330 A340                        
		public var STA_V1               :Number ; //APU自启动时间                                 
		public var STA_V1_EST_TIME      :Number ; //基于STA_V1到达红线值剩余时间（分钟）          
		public var EGTP_V1              :Number ; //APU自启动EGT峰值                              
		public var NPA_V1               :Number ; //APU自启动EGT峰值的转速 单位%                  
		public var OTA_V1               :Number ; //APU自启动滑油温度 A330 A340 LOT               
		public var LCIT_V1              :Number ; //APU自启动进气口温度            
		
		public var EGTA_MAX_COR         :Number ; //修正海平面标态EGT温度                         
		public var EGTA_MAX_COR_EST_TIME:Number ; //基于EGT_MAX_COR到达红线值剩余时间（分钟）     
		public var EGTA_ROLL5         :Number ;
		public var EGTA_OUT         :Number ;
		
		public var P2A_MAX_COR          :Number ; //修正海平面标态P2A进口压力，根据LCIT/ALT修正   
		public var PT_MAX_COR           :Number ; //APU最大EGT时的引气压力修正                    
		public var WB_MAX_COR           :Number ; //APU最大EGT时的引气流量 kg/sec 修正值          
		public var PT_MAX_COR_EST_TIME  :Number ; //基于PT_MAX_COR到达红线值剩余时间（分钟）      
		public var APR_MAX              :Number ; //修正海平面标态APU 引气出口与进口 的增压比PT_MAX_COR/P2A_MAX_COR 
		public var APR_MAX_EST_TIME     :Number ; //基于APR_MAX到达红线值剩余时间（分钟） 红线值 为 3.4 
		public var EGTP_V1_COR          :Number ; //APU自启动EGT峰值海平面标态修正                
		public var NPA_V1_COR           :Number ; //修正NPA数据 NPA/T1 流体近似性                 
		public var HOT_OT_MAX           :Number ; //高位话有温度 HOT-LCIT 的 温度 A330 A340       
		public var OT_MAX               :Number ; //APU最大EGT时的OTA-LCIT 温度                   
		public var OT_MAX_EST_TIME      :Number ; //基于OT_MAX到达红线值剩余时间（分钟） 红线值 为 不同型号APU此参数不同 
		public var OT_V1                :Number ; //APU自启动时滑油增温OTA_V1-LCIT_V1             
		public var RECDATETIME          :Number ; //更新时间
		
	}
}