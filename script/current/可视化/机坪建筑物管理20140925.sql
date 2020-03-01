CREATE SEQUENCE S_B_AIRPORT_BUILDING;

ALTER TABLE B_AIRPORT_BUILDING
   DROP CONSTRAINT FK_BUILDING_R_AIRPORT;

ALTER TABLE B_AIRPORT_BUILDING
   DROP PRIMARY KEY CASCADE;

/*==============================================================*/
/* Table: B_AIRPORT_BUILDING                                    */
/*==============================================================*/
CREATE TABLE B_AIRPORT_BUILDING  (
   ID                   NUMBER(18)                      NOT NULL,
   AIRPORTID            NUMBER(18),
   CODE                 VARCHAR2(255),
   NAME                 VARCHAR2(255),
   INFODESC             VARCHAR2(2000),
   IMAGEURL             VARCHAR2(511)                  DEFAULT 'com/apms/flex/assets/building/hangar_door.png',
   LONGITUDE            NUMBER(24,12)                  DEFAULT 0,
   LATITUDE             NUMBER(24,12)                  DEFAULT 0,
   X                    NUMBER(24,12)                  DEFAULT 0,
   Y                    NUMBER(24,12)                  DEFAULT 0,
   WIDTHSCALE           NUMBER(4,2)                    DEFAULT 1,
   HEIGHTSCALE          NUMBER(4,2)                    DEFAULT 1,
   ROTATION             NUMBER(4)                      DEFAULT 0,
   COMMENTS             VARCHAR2(1000),
   UPDATETIME           DATE,
   UPDATEUSER           VARCHAR2(255)
);

COMMENT ON TABLE B_AIRPORT_BUILDING IS
'机坪上的建筑';

COMMENT ON COLUMN B_AIRPORT_BUILDING.ID IS
'ID';

COMMENT ON COLUMN B_AIRPORT_BUILDING.AIRPORTID IS
'机场ID';

COMMENT ON COLUMN B_AIRPORT_BUILDING.CODE IS
'编号';

COMMENT ON COLUMN B_AIRPORT_BUILDING.NAME IS
'名称';

COMMENT ON COLUMN B_AIRPORT_BUILDING.INFODESC IS
'信息描述';

COMMENT ON COLUMN B_AIRPORT_BUILDING.IMAGEURL IS
'图标路径';

COMMENT ON COLUMN B_AIRPORT_BUILDING.LONGITUDE IS
'经度';

COMMENT ON COLUMN B_AIRPORT_BUILDING.LATITUDE IS
'纬度';

COMMENT ON COLUMN B_AIRPORT_BUILDING.X IS
'X坐标';

COMMENT ON COLUMN B_AIRPORT_BUILDING.Y IS
'Y坐标';

COMMENT ON COLUMN B_AIRPORT_BUILDING.WIDTHSCALE IS
'宽度缩放比';

COMMENT ON COLUMN B_AIRPORT_BUILDING.HEIGHTSCALE IS
'高度缩放比';

COMMENT ON COLUMN B_AIRPORT_BUILDING.ROTATION IS
'旋转角度';

COMMENT ON COLUMN B_AIRPORT_BUILDING.COMMENTS IS
'备注';

COMMENT ON COLUMN B_AIRPORT_BUILDING.UPDATETIME IS
'更新时间';

COMMENT ON COLUMN B_AIRPORT_BUILDING.UPDATEUSER IS
'更新人';

ALTER TABLE B_AIRPORT_BUILDING
   ADD CONSTRAINT PK_B_AIRPORT_BUILDING PRIMARY KEY (ID);

ALTER TABLE B_AIRPORT_BUILDING
   ADD CONSTRAINT FK_BUILDING_R_AIRPORT FOREIGN KEY (AIRPORTID)
      REFERENCES B_AIRPORT (ID);
