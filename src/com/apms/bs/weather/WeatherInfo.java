package com.apms.bs.weather;

import java.util.Date;

import com.apms.bs.util.DateUtil;

/**
 * 国家气象局提供的天气预报接口
 * http://m.weather.com.cn/data/101010100.html //此接口从2014年3月14号后已不更新
 * 新的接口(网上找的)
 * http://weather.51wnl.com/weatherinfo/GetMoreWeather?cityCode=101040100&weatherType=0 
 * 本接口返回6天的数据，返回信息比较全面，也是以json格式提供，对应的Bean
 * @author zzy
 *
 */
public class WeatherInfo {
	
	//基本信息;                                                                                                    
    private String city; //北京"
    private String city_en; //beijing                                                           
    private String date_y; //2012年2月16日"
    private Date date; // 
    private String week; //星期四
    private String fchh; //11 
    private String cityid; //101010100
    
    //摄氏温度                                                                                  
    private String temp1; //2℃~-7℃                                                                          
    private String temp2; //1℃~-7℃                                                                          
    private String temp3; //4℃~-7℃                                                                          
    private String temp4; //7℃~-5℃                                                                          
    private String temp5; //5℃~-3℃                                                                          
    private String temp6; //5℃~-2℃                                                                          
    
    //华氏温度;                                                                                 
    private String tempF1; //35.6℉~19.4℉                                                                    
    private String tempF2; //33.8℉~19.4℉                                                                    
    private String tempF3; //39.2℉~19.4℉                                                                    
    private String tempF4; //44.6℉~23℉                                                                      
    private String tempF5; //41℉~26.6℉                                                                      
    private String tempF6; //41℉~28.4℉                                                                      
    
    //天气描述;                                                                                 
    private String weather1; //晴                                                                             
    private String weather2; //晴                                                                             
    private String weather3; //晴                                                                             
    private String weather4; //晴转多云                                                                       
    private String weather5; //多云                                                                           
    private String weather6; //多云转阴                                                                       
    
    //天气描述图片序号                                                                          
    private String img1; //0                                                                                  
    private String img2; //99                                                                                 
    private String img3; //0                                                                                  
    private String img4; //99                                                                                 
    private String img5; //0                                                                                  
    private String img6; //99                                                                                 
    private String img7; //0                                                                                  
    private String img8; //1                                                                                  
    private String img9; //1                                                                                  
    private String img10; //99                                                                                
    private String img11; //1                                                                                 
    private String img12; //2                                                                                 
    private String img_single; //0                                                                            
    
    //图片名称;                                                                                 
    private String img_title1; //晴                                                                           
    private String img_title2; //晴                                                                           
    private String img_title3; //晴                                                                           
    private String img_title4; //晴                                                                           
    private String img_title5; //晴                                                                           
    private String img_title6; //晴                                                                           
    private String img_title7; //晴                                                                           
    private String img_title8; //多云                                                                         
    private String img_title9; //多云                                                                         
    private String img_title10; //多云                                                                        
    private String img_title11; //多云                                                                        
    private String img_title12; //阴                                                                          
    private String img_title_single; //晴                                                                     
    
    //风速描述                                                                                  
    private String wind1; //北风3-4级转微风                                                                   
    private String wind2; //微风                                                                              
    private String wind3; //微风                                                                              
    private String wind4; //微风                                                                              
    private String wind5; //微风                                                                              
    private String wind6; //微风                                                                              
    
    //风速级别描述                                                                              
    private String fx1; //北风                                                                                
    private String fx2; //微风                                                                                
    private String fl1; //3-4级转小于3级                                                                      
    private String fl2; //小于3级                                                                             
    private String fl3; //小于3级                                                                             
    private String fl4; //小于3级                                                                             
    private String fl5; //小于3级                                                                             
    private String fl6; //小于3级                                                                             
    
    //今天穿衣指数;                                                                             
    private String index; //冷                                                                                
    private String index_d; //天气冷，建议着棉衣、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣或冬大衣。    
    
    //48小时穿衣指数                                                                            
    private String index48; //冷                                                                              
    private String index48_d; //天气冷，建议着棉衣、皮夹克加羊毛衫等冬季服装。年老体弱者宜着厚棉衣或冬大衣。  

    //紫外线及48小时紫外线                                                                      
    private String index_uv; //弱                                                                             
    private String index48_uv; //弱                                                                           
    //洗车                                                                                      
    private String index_xc; //适宜                                                                           
    //旅游                                                                                      
    private String index_tr; //一般                                                                           
    //舒适指数                                                                                  
    private String index_co; //较不舒适                                                                       
    
    //下面是什么？
    private String st1; //1                                                                                   
    private String st2; //-8                                                                                  
    private String st3; //2                                                                                   
    private String st4; //-4                                                                                  
    private String st5; //5                                                                                   
    private String st6; //-5
    
    public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity_en() {
		return city_en;
	}
	public void setCity_en(String city_en) {
		this.city_en = city_en;
	}
	public String getDate_y() {
		return date_y;
	}
	public void setDate_y(String date_y) {
		this.date_y = date_y;
	}
	public Date getDate() {
		//StringToDate
		date = DateUtil.StringToDate(date_y, "yyyy年MM月dd日");
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public String getFchh() {
		return fchh;
	}
	public void setFchh(String fchh) {
		this.fchh = fchh;
	}
	public String getCityid() {
		return cityid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getTemp3() {
		return temp3;
	}
	public void setTemp3(String temp3) {
		this.temp3 = temp3;
	}
	public String getTemp4() {
		return temp4;
	}
	public void setTemp4(String temp4) {
		this.temp4 = temp4;
	}
	public String getTemp5() {
		return temp5;
	}
	public void setTemp5(String temp5) {
		this.temp5 = temp5;
	}
	public String getTemp6() {
		return temp6;
	}
	public void setTemp6(String temp6) {
		this.temp6 = temp6;
	}
	public String getTempF1() {
		return tempF1;
	}
	public void setTempF1(String tempF1) {
		this.tempF1 = tempF1;
	}
	public String getTempF2() {
		return tempF2;
	}
	public void setTempF2(String tempF2) {
		this.tempF2 = tempF2;
	}
	public String getTempF3() {
		return tempF3;
	}
	public void setTempF3(String tempF3) {
		this.tempF3 = tempF3;
	}
	public String getTempF4() {
		return tempF4;
	}
	public void setTempF4(String tempF4) {
		this.tempF4 = tempF4;
	}
	public String getTempF5() {
		return tempF5;
	}
	public void setTempF5(String tempF5) {
		this.tempF5 = tempF5;
	}
	public String getTempF6() {
		return tempF6;
	}
	public void setTempF6(String tempF6) {
		this.tempF6 = tempF6;
	}
	public String getWeather1() {
		return weather1;
	}
	public void setWeather1(String weather1) {
		this.weather1 = weather1;
	}
	public String getWeather2() {
		return weather2;
	}
	public void setWeather2(String weather2) {
		this.weather2 = weather2;
	}
	public String getWeather3() {
		return weather3;
	}
	public void setWeather3(String weather3) {
		this.weather3 = weather3;
	}
	public String getWeather4() {
		return weather4;
	}
	public void setWeather4(String weather4) {
		this.weather4 = weather4;
	}
	public String getWeather5() {
		return weather5;
	}
	public void setWeather5(String weather5) {
		this.weather5 = weather5;
	}
	public String getWeather6() {
		return weather6;
	}
	public void setWeather6(String weather6) {
		this.weather6 = weather6;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public String getImg6() {
		return img6;
	}
	public void setImg6(String img6) {
		this.img6 = img6;
	}
	public String getImg7() {
		return img7;
	}
	public void setImg7(String img7) {
		this.img7 = img7;
	}
	public String getImg8() {
		return img8;
	}
	public void setImg8(String img8) {
		this.img8 = img8;
	}
	public String getImg9() {
		return img9;
	}
	public void setImg9(String img9) {
		this.img9 = img9;
	}
	public String getImg10() {
		return img10;
	}
	public void setImg10(String img10) {
		this.img10 = img10;
	}
	public String getImg11() {
		return img11;
	}
	public void setImg11(String img11) {
		this.img11 = img11;
	}
	public String getImg12() {
		return img12;
	}
	public void setImg12(String img12) {
		this.img12 = img12;
	}
	public String getImg_single() {
		return img_single;
	}
	public void setImg_single(String img_single) {
		this.img_single = img_single;
	}
	public String getImg_title1() {
		return img_title1;
	}
	public void setImg_title1(String img_title1) {
		this.img_title1 = img_title1;
	}
	public String getImg_title2() {
		return img_title2;
	}
	public void setImg_title2(String img_title2) {
		this.img_title2 = img_title2;
	}
	public String getImg_title3() {
		return img_title3;
	}
	public void setImg_title3(String img_title3) {
		this.img_title3 = img_title3;
	}
	public String getImg_title4() {
		return img_title4;
	}
	public void setImg_title4(String img_title4) {
		this.img_title4 = img_title4;
	}
	public String getImg_title5() {
		return img_title5;
	}
	public void setImg_title5(String img_title5) {
		this.img_title5 = img_title5;
	}
	public String getImg_title6() {
		return img_title6;
	}
	public void setImg_title6(String img_title6) {
		this.img_title6 = img_title6;
	}
	public String getImg_title7() {
		return img_title7;
	}
	public void setImg_title7(String img_title7) {
		this.img_title7 = img_title7;
	}
	public String getImg_title8() {
		return img_title8;
	}
	public void setImg_title8(String img_title8) {
		this.img_title8 = img_title8;
	}
	public String getImg_title9() {
		return img_title9;
	}
	public void setImg_title9(String img_title9) {
		this.img_title9 = img_title9;
	}
	public String getImg_title10() {
		return img_title10;
	}
	public void setImg_title10(String img_title10) {
		this.img_title10 = img_title10;
	}
	public String getImg_title11() {
		return img_title11;
	}
	public void setImg_title11(String img_title11) {
		this.img_title11 = img_title11;
	}
	public String getImg_title12() {
		return img_title12;
	}
	public void setImg_title12(String img_title12) {
		this.img_title12 = img_title12;
	}
	public String getImg_title_single() {
		return img_title_single;
	}
	public void setImg_title_single(String img_title_single) {
		this.img_title_single = img_title_single;
	}
	public String getWind1() {
		return wind1;
	}
	public void setWind1(String wind1) {
		this.wind1 = wind1;
	}
	public String getWind2() {
		return wind2;
	}
	public void setWind2(String wind2) {
		this.wind2 = wind2;
	}
	public String getWind3() {
		return wind3;
	}
	public void setWind3(String wind3) {
		this.wind3 = wind3;
	}
	public String getWind4() {
		return wind4;
	}
	public void setWind4(String wind4) {
		this.wind4 = wind4;
	}
	public String getWind5() {
		return wind5;
	}
	public void setWind5(String wind5) {
		this.wind5 = wind5;
	}
	public String getWind6() {
		return wind6;
	}
	public void setWind6(String wind6) {
		this.wind6 = wind6;
	}
	public String getFx1() {
		return fx1;
	}
	public void setFx1(String fx1) {
		this.fx1 = fx1;
	}
	public String getFx2() {
		return fx2;
	}
	public void setFx2(String fx2) {
		this.fx2 = fx2;
	}
	public String getFl1() {
		return fl1;
	}
	public void setFl1(String fl1) {
		this.fl1 = fl1;
	}
	public String getFl2() {
		return fl2;
	}
	public void setFl2(String fl2) {
		this.fl2 = fl2;
	}
	public String getFl3() {
		return fl3;
	}
	public void setFl3(String fl3) {
		this.fl3 = fl3;
	}
	public String getFl4() {
		return fl4;
	}
	public void setFl4(String fl4) {
		this.fl4 = fl4;
	}
	public String getFl5() {
		return fl5;
	}
	public void setFl5(String fl5) {
		this.fl5 = fl5;
	}
	public String getFl6() {
		return fl6;
	}
	public void setFl6(String fl6) {
		this.fl6 = fl6;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getIndex_d() {
		return index_d;
	}
	public void setIndex_d(String index_d) {
		this.index_d = index_d;
	}
	public String getIndex48() {
		return index48;
	}
	public void setIndex48(String index48) {
		this.index48 = index48;
	}
	public String getIndex48_d() {
		return index48_d;
	}
	public void setIndex48_d(String index48_d) {
		this.index48_d = index48_d;
	}
	public String getIndex_uv() {
		return index_uv;
	}
	public void setIndex_uv(String index_uv) {
		this.index_uv = index_uv;
	}
	public String getIndex48_uv() {
		return index48_uv;
	}
	public void setIndex48_uv(String index48_uv) {
		this.index48_uv = index48_uv;
	}
	public String getIndex_xc() {
		return index_xc;
	}
	public void setIndex_xc(String index_xc) {
		this.index_xc = index_xc;
	}
	public String getIndex_tr() {
		return index_tr;
	}
	public void setIndex_tr(String index_tr) {
		this.index_tr = index_tr;
	}
	public String getIndex_co() {
		return index_co;
	}
	public void setIndex_co(String index_co) {
		this.index_co = index_co;
	}
	public String getSt1() {
		return st1;
	}
	public void setSt1(String st1) {
		this.st1 = st1;
	}
	public String getSt2() {
		return st2;
	}
	public void setSt2(String st2) {
		this.st2 = st2;
	}
	public String getSt3() {
		return st3;
	}
	public void setSt3(String st3) {
		this.st3 = st3;
	}
	public String getSt4() {
		return st4;
	}
	public void setSt4(String st4) {
		this.st4 = st4;
	}
	public String getSt5() {
		return st5;
	}
	public void setSt5(String st5) {
		this.st5 = st5;
	}
	public String getSt6() {
		return st6;
	}
	public void setSt6(String st6) {
		this.st6 = st6;
	}
	public String getIndex_cl() {
		return index_cl;
	}
	public void setIndex_cl(String index_cl) {
		this.index_cl = index_cl;
	}
	public String getIndex_ls() {
		return index_ls;
	}
	public void setIndex_ls(String index_ls) {
		this.index_ls = index_ls;
	}
	public String getIndex_ag() {
		return index_ag;
	}
	public void setIndex_ag(String index_ag) {
		this.index_ag = index_ag;
	}
	//晨练                                                                                      
    private String index_cl; //较不宜                                                                         
    //晾晒                                                                                      
    private String index_ls; //基本适宜                                                                       
    //过敏                                                                                      
    private String index_ag; //极不易发"   
    
    /**
     * 
     * {
    "weatherinfo": {
        "city": "阜阳",
        "city_en": "",
        "date_y": "2015年08月31日",
        "date": "",
        "week": "",
        "fchh": 0,
        "cityid": 101220801,
        "temp1": "20℃~31℃",
        "temp2": "19℃~29℃",
        "temp3": "18℃~30℃",
        "temp4": "21℃~30℃",
        "temp5": "22℃~30℃",
        "temp6": "20℃~29℃",
        "tempF1": "",
        "tempF2": "",
        "tempF3": "",
        "tempF4": "",
        "tempF5": "",
        "tempF6": "",
        "weather1": "多云转小雨",
        "weather2": "雷阵雨转晴",
        "weather3": "多云",
        "weather4": "多云",
        "weather5": "小雨",
        "weather6": "小雨转多云",
        "img1": "1",
        "img2": "7",
        "img3": "4",
        "img4": "0",
        "img5": "1",
        "img6": "1",
        "img7": "1",
        "img8": "1",
        "img9": "7",
        "img10": "7",
        "img11": "7",
        "img12": "1",
        "img_single": 0,
        "img_title1": "",
        "img_title2": "",
        "img_title3": "",
        "img_title4": "",
        "img_title5": "",
        "img_title6": "",
        "img_title7": "",
        "img_title8": "",
        "img_title9": "",
        "img_title10": "",
        "img_title11": "",
        "img_title12": "",
        "img_title_single": "",
        "wind1": "",
        "wind2": "",
        "wind3": "",
        "wind4": "",
        "wind5": "",
        "wind6": "",
        "fx1": "",
        "fx2": "",
        "fl1": "",
        "fl2": "",
        "fl3": "",
        "fl4": "",
        "fl5": "",
        "fl6": "",
        "index": "",
        "index_d": "",
        "index48": "",
        "index48_d": "",
        "index_uv": "",
        "index48_uv": "",
        "index_xc": "",
        "index_tr": "",
        "index_co": "",
        "st1": 0,
        "st2": 0,
        "st3": 0,
        "st4": 0,
        "st5": 0,
        "st6": 0,
        "index_cl": "",
        "index_ls": "",
        "index_ag": ""
		    }
		}
     */


}
