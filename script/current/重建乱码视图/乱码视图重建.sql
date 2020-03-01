CREATE OR REPLACE VIEW V_BAM_ANALYZEVIEW_TREE AS
SELECT 'F'||BF.ID AS ID ,
       bf.id pid,
       decode(BF.PARENTID,null,null,'F'||BF.PARENTID) AS PARENTID,
       name,
       code,
       '5' TYPE,
       '分析视图目录' TYPECN,
       BF.DESCRIPTION METADATA
FROM BAM_FOLDER BF WHERE BF.TYPE=5
UNION
SELECT
     'D'||BB.ID AS ID,
     bb.id pid,
     decode(BB.FOLDERID,null,null,'F'||BB.FOLDERID) AS PARENTID,
     name,
     code,
     'ANALYZEVIEW' TYPE,
     '分析视图' TYPECN,
     to_char(BB.METADATA) METADATA
     FROM BAM_ANALYZEVIEW BB;

     
CREATE OR REPLACE VIEW V_BAM_BUSINESSSCENARIO_TREE AS
SELECT 'F'||BF.ID AS ID ,
       BF.ID PID,
       DECODE(BF.PARENTID,NULL,NULL,'F'||BF.PARENTID) AS PARENTID,
       NAME,
       CODE,
       '0' TYPE,
       '业务场景目录' TYPECN,
       BF.DESCRIPTION
FROM BAM_FOLDER BF WHERE BF.TYPE=0
UNION
SELECT
     'S'||BB.ID AS ID,
     BB.ID PID,
     'F'||BB.FOLDERID AS PARENTID,
     NAME,
     CODE,
     '1' TYPE,
     '业务场景' TYPECN,
     BB.DESCRIPTION
     FROM BAM_BUSINESSSCENARIO BB;
     
     
CREATE OR REPLACE VIEW V_BAM_BUSINESSVIEW_TREE AS
SELECT 'F'||BF.ID AS ID ,
       bf.id pid,
       decode(BF.PARENTID,null,null,'F'||BF.PARENTID) AS PARENTID,
       name,
       code,
       '3' TYPE,
       '业务视图目录' TYPECN,
       BF.DESCRIPTION,
       '' STREAMMODULENAME,
       '' STREAMWINDOWNAME,
       '' PROVIDERNAME,
       -1 STATUS
FROM BAM_FOLDER BF WHERE BF.TYPE=3
UNION
SELECT
     'D'||BB.ID AS ID,
     bb.id pid,
     decode(BB.FOLDERID,null,null,'F'||BB.FOLDERID) AS PARENTID,
     name,
     code,
     'BUSINESSVIEW' TYPE,
     '业务视图' TYPECN,
     BB.DESCRIPTION,
     BB.STREAMMODULENAME,
     BB.STREAMWINDOWNAME,
     BB.PROVIDERNAME,
     BB.STATUS
     FROM V_BAM_BUSINESSVIEW BB;
     
CREATE OR REPLACE VIEW V_BAM_DASHBOARDOBJECT_TREE AS
SELECT 'F'||BF.ID AS ID ,
       bf.id pid,
       decode(BF.PARENTID,null,null,'F'||BF.PARENTID) AS PARENTID,
       name,
       code,
       '2' TYPE,
       '仪表盘对象目录' TYPECN,
       BF.DESCRIPTION,
       '' mtcode
FROM BAM_FOLDER BF WHERE BF.TYPE=2
UNION
SELECT
     'D'||BB.ID AS ID,
     bb.id pid,
     decode(BB.FOLDERID,null,null,'F'||BB.FOLDERID) AS PARENTID,
     name,
     code,
     'DASHBOARDOBJECT' TYPE,
     '仪表盘对象' TYPECN,
     BB.DESCRIPTION,
     BB.Mtcode mtcode
     FROM Bam_Dashboardobject BB;

CREATE OR REPLACE VIEW V_BAM_DASHBOARD_TREE AS
SELECT 'F'||BF.ID AS ID ,
       bf.id pid,
       decode(BF.PARENTID,null,null,'F'||BF.PARENTID) AS PARENTID,
       name,
       code,
       '1' TYPE,
       '仪表盘目录' TYPECN,
       BF.DESCRIPTION,
       '' MTCODE,
       1 REFRESHINTERVAL,
       1 SEQ,
       'Y' ISFOLDER
FROM BAM_FOLDER BF WHERE BF.TYPE=1
UNION
SELECT
     'D'||BB.ID AS ID,
     bb.id pid,
     decode(BB.FOLDERID,null,null,'F'||BB.FOLDERID) AS PARENTID,
     name,
     code,
     'DASHBOARD' TYPE,
     '仪表盘' TYPECN,
     BB.DESCRIPTION,
     BB.LAYOUT_MTCODE MTCODE,
     BB.REFRESHINTERVAL,
     BB.SEQ,
     'N' ISFOLDER
     FROM Bam_Dashboard BB;

CREATE OR REPLACE VIEW V_BAM_QUERYVIEW_TREE AS
SELECT 'F'||BF.ID AS ID ,
       bf.id pid,
       decode(BF.PARENTID,null,null,'F'||BF.PARENTID) AS PARENTID,
       name,
       code,
       '4' TYPE,
       '查询视图目录' TYPECN,
       BF.DESCRIPTION
FROM BAM_FOLDER BF WHERE BF.TYPE=4
UNION
SELECT
     'D'||BB.ID AS ID,
     bb.id pid,
     decode(BB.FOLDERID,null,null,'F'||BB.FOLDERID) AS PARENTID,
     name,
     code,
     'QUERYVIEW' TYPE,
     '查询视图' TYPECN,
     BB.DESCRIPTION
     FROM BAM_QUERYVIEW BB;



CREATE OR REPLACE VIEW V_BAM_DASHBOARD_AUTHTREE AS
SELECT --create by zhangzy 2011/8/22仪表盘对象授权
       'F'||BF.ID AS ID ,
       bf.id PID,
       decode(BF.PARENTID,null,null,'F'||BF.PARENTID) AS PARENTID,
       NAME,
       CODE,
       '仪表盘目录' TYPECN,
       1 SEQ,
       'Y' ISFOLDER
FROM BAM_FOLDER BF WHERE BF.TYPE=1
UNION
SELECT
     'D'||BB.ID AS ID,
     bb.id PID,
     decode(BB.FOLDERID,null,null,'F'||BB.FOLDERID) AS PARENTID,
     NAME,
     CODE,
     '仪表盘' TYPECN,
     BB.SEQ,
     'N' ISFOLDER
     FROM Bam_Dashboard BB;


     