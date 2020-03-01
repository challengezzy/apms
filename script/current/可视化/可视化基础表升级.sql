
--   查询外键约束(查某表的所有父表)   
  select   c.constraint_name,cc.column_name,rcc.owner,rcc.table_name,rcc.column_name   
  from   user_constraints   c,user_cons_columns   cc,user_cons_columns   rcc   
  where   c.owner='SALIEN_SBGL'   
  and   c.table_name='JH_NDGXLGJH_TB'   
  and   c.constraint_type='R'   
  and   c.owner=cc.owner   
  and   c.constraint_name=cc.constraint_name   
  and   c.r_owner=rcc.owner   
  and   c.r_constraint_name=rcc.constraint_name   
  and   cc.position=rcc.position   
  order   by   c.constraint_name,cc.position;   
    
  --查询连接到某表的所有外键(查某表的所有子表)   
  select   rcc.owner,rcc.table_name,rcc.constraint_name,rcc.column_name,c.table_name,c.constraint_name,cc.column_name       
  from   user_constraints   c,user_cons_columns   cc,user_cons_columns   rcc   
  where   lower(c.owner)='nm'   
  and   rcc.table_name='PDHSUBSYSTEM'   
  and   c.constraint_type='R'   
  and   c.owner=cc.owner   
  and   c.constraint_name=cc.constraint_name   
  and   c.r_owner=rcc.owner   
  and   c.r_constraint_name=rcc.constraint_name   
  and   cc.position=rcc.position   
  order   by   c.constraint_name,cc.position 

--备份基础数据表
CREATE TABLE BAKT3_B_AIRCRAFT           AS SELECT * FROM  B_AIRCRAFT      ;
CREATE TABLE BAKT3_B_AIRCRAFT_MODEL     AS SELECT * FROM  B_AIRCRAFT_MODEL;
CREATE TABLE BAKT3_B_AIRPORT            AS SELECT * FROM  B_AIRPORT       ;
CREATE TABLE BAKT3_B_ORGANIZATION       AS SELECT * FROM  B_ORGANIZATION  ;


--飞机 添加航空公司AIRLINEID一个字段
ALTER TABLE B_AIRCRAFT ADD AIRLINEID NUMBER(18);
COMMENT ON COLUMN B_AIRCRAFT.AIRLINEID IS '航空公司ID';

--机型	B_AIRCRAFT_MODEL	字段修改,表需要重建
--表重建脚本,注意先删除外建关联

INSERT INTO B_AIRCRAFT_MODEL(ID,MODELCODE,MODELSERIES)
SELECT ID,ABBREVIATION,DESCRIPTION FROM BAKT3_B_AIRCRAFT_MODEL;

--机场	B_AIRPORT	添加是否高原机场、是否除冰
INSERT INTO B_AIRPORT(ID,REGIONID,CODE_3,CODE_4,NAME,NAMEEN,T_ZONE,ALTITUDE,LONGITUDE,LATITUDE,COMMENTS,UPDATEUSER,UPDATETIME)
SELECT ID,REGIONID,CODE_3,CODE_4,NAME,NAMEEN,T_ZONE,ALTITUDE,LONGITUDE,LATITUDE,COMMENTS,UPDATE_MAN,UPDATE_DATE
FROM BAKT3_B_AIRPORT WHERE REGIONID NOT IN (216,218);

--组织	B_ORGANIZATION	添加组织代码、工作机场ID、航空公司ID、更新人、更新时间
insert into b_organization(id,name,code,parentorgid,orglevel,comments,updatetime)
select * From bakt3_b_organization;

--用户	B_USER	新增表替代PUB_USER管理用户信息，更新时数据同步到PUB_USER.
--先同步PUB_USER信息到B_USER表。

--城市区域	B_REGION	修改索引
--城市天气	B_WEATHER	修改索引




