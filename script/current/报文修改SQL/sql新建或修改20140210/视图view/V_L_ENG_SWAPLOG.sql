CREATE OR REPLACE VIEW V_L_ENG_SWAPLOG AS
SELECT ID,
AIRCRAFTID,
ENG_MODEL,
ENGINE_POSTION,
ENGSN,
SWAP_DATE,
SWAP_DATE_STR,
SWAP_REASON,
SWAP_ACTION,
FAULT_DESC,
ROUND((t.TIME_ONINSTALL/60),2)   TIME_ONINSTALL_HOUR,
CYCLE_ONINSTALL,
ROUND((t.TIME_ONREPAIRED/60),2) TIME_ONREPAIRED_HOUR,
CYCLE_ONREPAIRED,
ROUND((t.TIME_ONREMOVE/60),2) TIME_ONREMOVE_HOUR,
CYCLE_ONREMOVE,
REPAIR_FLAG,
COMMENTS,
UPDATE_MAN,
UPDATE_TIME
from L_ENG_SWAPLOG t;