package com.sep.flex.modules
{
	import com.sep.flex.modules.buttonListener.AirMaterialListener;
	import com.sep.flex.modules.buttonListener.ApplicabilityListerer;
	import com.sep.flex.modules.buttonListener.AviationMaterialsListener;
	import com.sep.flex.modules.buttonListener.ChemiacalListener;
	import com.sep.flex.modules.buttonListener.ErrorHistoryListener;
	import com.sep.flex.modules.buttonListener.JobCreateListener;
	import com.sep.flex.modules.buttonListener.KeyPointListener;
	import com.sep.flex.modules.buttonListener.PrintA3PromptCardListener;
	import com.sep.flex.modules.buttonListener.ToolsListListener;
	import com.sep.flex.modules.buttonListener.TrainingMaterialListener;
	import com.sep.flex.modules.fileManager.JKAttachDownloadBtnListener;
	import com.sep.flex.modules.fileManager.JKAttachManageOfListener;
	import com.sep.flex.modules.fileManager.TMAttachDownloadBtnListener;
	import com.sep.flex.modules.fileManager.TMAttachManageOfListener;
	import com.sep.flex.modules.nrcard.PrintNrCardListener;
	
	import flash.display.Sprite;
 
	
	
	/**
	 * SEP所有自定义监听器、拦截器类的引用 
	 **/
	public class SepCustomLibrary extends Sprite
	{
		private var tMAttachManageOfListener:TMAttachManageOfListener;
		private var tMAttachDownloadBtnListener:TMAttachDownloadBtnListener;
		private var jKAttachDownloadBtnListener:JKAttachDownloadBtnListener;
		private var jKAttachManageOfListener:JKAttachManageOfListener;
		private var keyPointListener:KeyPointListener;
		private var trainingMaterialListener:TrainingMaterialListener;
		private var applicabilityListerer:ApplicabilityListerer;
		private var errorHistoryListener:ErrorHistoryListener;
		private var jobCreateListener:JobCreateListener;
		private var aviationMaterialsListener:AviationMaterialsListener;
		private var printA3PromptCardListener:PrintA3PromptCardListener;
		private var chemiacalListener:ChemiacalListener;
		private var toolsListListener:ToolsListListener;
		private var airMaterialListener:AirMaterialListener;
		
		private var printNrCardListener:PrintNrCardListener;

	}
}