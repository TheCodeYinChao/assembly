package com.raydata.core.image;

import org.activiti.bpmn.model.*;
import org.activiti.bpmn.model.Process;
import org.activiti.image.impl.DefaultProcessDiagramCanvas;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

import java.io.InputStream;
import java.util.List;

/**
 * //将父类中的generateProcessDiagram方法代码全部复制到这个方法，并且重写HMProcessDiagramCanvas方法。
 * @author shareniu
 * 重写DefaultProcessDiagramCanvas类，该类中的initialize方法负责图片资源的加载。
 * 重写DefaultProcessDiagramGenerator类，然后使用自定义的HMProcessDiagramCanvas类。
 * 
 */
public class ProcessDiagramGenerator extends DefaultProcessDiagramGenerator {
	public InputStream generateDiagram(BpmnModel bpmnModel, String imageType,
                                       List<String> highLightedActivities, List<String> highLightedFlows,
                                       String activityFontName, String labelFontName,
                                       String annotationFontName, ClassLoader customClassLoader,
                                       double scaleFactor) {

		return generateProcessDiagram(bpmnModel, imageType,
				highLightedActivities, highLightedFlows, activityFontName,
				labelFontName, annotationFontName, customClassLoader,
				scaleFactor).generateImage(imageType);
	}
	  private static void drawHighLight(DefaultProcessDiagramCanvas processDiagramCanvas, GraphicInfo graphicInfo) {
		    processDiagramCanvas.drawHighLight((int) graphicInfo.getX(), (int) graphicInfo.getY(), (int) graphicInfo.getWidth(), (int) graphicInfo.getHeight());

		  }
	@Override
	protected void drawActivity(
            DefaultProcessDiagramCanvas processDiagramCanvas,
            BpmnModel bpmnModel, FlowNode flowNode,
            List<String> highLightedActivities, List<String> highLightedFlows,
            double scaleFactor) {
		 ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(flowNode.getClass());
		    if (drawInstruction != null) {
		      
		      drawInstruction.draw(processDiagramCanvas, bpmnModel, flowNode);

		      // Gather info on the multi instance marker
		      boolean multiInstanceSequential = false, multiInstanceParallel = false, collapsed = false;
		      if (flowNode instanceof Activity) {
		        Activity activity = (Activity) flowNode;
		        MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics = activity.getLoopCharacteristics();
		        if (multiInstanceLoopCharacteristics != null) {
		          multiInstanceSequential = multiInstanceLoopCharacteristics.isSequential();
		          multiInstanceParallel = !multiInstanceSequential;
		        }
		      }

		      // Gather info on the collapsed marker
		      GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(flowNode.getId());
		      if (flowNode instanceof SubProcess) {
		        collapsed = graphicInfo.getExpanded() != null && !graphicInfo.getExpanded();
		      } else if (flowNode instanceof CallActivity) {
		        collapsed = true;
		      }

		      if (scaleFactor == 1.0) {
		        // Actually draw the markers
		        processDiagramCanvas.drawActivityMarkers((int) graphicInfo.getX(), (int) graphicInfo.getY(),(int) graphicInfo.getWidth(), (int) graphicInfo.getHeight(), 
		                multiInstanceSequential, multiInstanceParallel, collapsed);
		      }

		      // Draw highlighted activities
		      if (highLightedActivities.contains(flowNode.getId())) {
		        drawHighLight(processDiagramCanvas, bpmnModel.getGraphicInfo(flowNode.getId()));
		      }

		    }
		    
		    // Outgoing transitions of activity
		    for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
		      boolean highLighted = (highLightedFlows.contains(sequenceFlow.getId()));
		      String defaultFlow = null;
		      if (flowNode instanceof Activity) {
		        defaultFlow = ((Activity) flowNode).getDefaultFlow();
		      } else if (flowNode instanceof Gateway) {
		        defaultFlow = ((Gateway) flowNode).getDefaultFlow();
		      }
		      
		      boolean isDefault = false;
		      if (defaultFlow != null && defaultFlow.equalsIgnoreCase(sequenceFlow.getId())) {
		        isDefault = true;
		      }
		      boolean drawConditionalIndicator = sequenceFlow.getConditionExpression() != null && !(flowNode instanceof Gateway);
		      
		      String sourceRef = sequenceFlow.getSourceRef();
		      String targetRef = sequenceFlow.getTargetRef();
		      FlowElement sourceElement = bpmnModel.getFlowElement(sourceRef);
		      FlowElement targetElement = bpmnModel.getFlowElement(targetRef);
		      List<GraphicInfo> graphicInfoList = bpmnModel.getFlowLocationGraphicInfo(sequenceFlow.getId());
		      if (graphicInfoList != null && graphicInfoList.size() > 0) {
		        graphicInfoList = connectionPerfectionizer(processDiagramCanvas, bpmnModel, sourceElement, targetElement, graphicInfoList);
		        int xPoints[]= new int[graphicInfoList.size()];
		        int yPoints[]= new int[graphicInfoList.size()];
		        
		        for (int i=1; i<graphicInfoList.size(); i++) {
		          GraphicInfo graphicInfo = graphicInfoList.get(i);
		          GraphicInfo previousGraphicInfo = graphicInfoList.get(i-1);
		          
		          if (i == 1) {
		            xPoints[0] = (int) previousGraphicInfo.getX();
		            yPoints[0] = (int) previousGraphicInfo.getY();
		          }
		          xPoints[i] = (int) graphicInfo.getX();
		          yPoints[i] = (int) graphicInfo.getY();
		          
		        }
		  
		        processDiagramCanvas.drawSequenceflow(xPoints, yPoints, drawConditionalIndicator, isDefault, highLighted, scaleFactor);
		  
		        // Draw sequenceflow label
		        GraphicInfo labelGraphicInfo = bpmnModel.getLabelGraphicInfo(sequenceFlow.getId());
		        if (labelGraphicInfo != null) {
		          processDiagramCanvas.drawLabel(sequenceFlow.getName(), labelGraphicInfo, false);
		        }else {
		        	//
					GraphicInfo lineCenter = getLineCenter(graphicInfoList);
					 processDiagramCanvas.drawLabel(sequenceFlow.getName(), lineCenter, false); 
				}
		      }
		    }

		    // Nested elements
		    if (flowNode instanceof FlowElementsContainer) {
		      for (FlowElement nestedFlowElement : ((FlowElementsContainer) flowNode).getFlowElements()) {
		        if (nestedFlowElement instanceof FlowNode) {
		          drawActivity(processDiagramCanvas, bpmnModel, (FlowNode) nestedFlowElement,
		              highLightedActivities, highLightedFlows, scaleFactor);
		        }
		      }
		    }
	}
	protected DefaultProcessDiagramCanvas generateProcessDiagram(
            BpmnModel bpmnModel, String imageType,
            List<String> highLightedActivities, List<String> highLightedFlows,
            String activityFontName, String labelFontName,
            String annotationFontName, ClassLoader customClassLoader,
            double scaleFactor) {
	
		prepareBpmnModel(bpmnModel);

		DefaultProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(
				bpmnModel, imageType, activityFontName, labelFontName,
				annotationFontName, customClassLoader);
		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			processDiagramCanvas.drawPoolOrLane(pool.getName(), graphicInfo);
		}
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane lane : process.getLanes()) {
				GraphicInfo graphicInfo = bpmnModel
						.getGraphicInfo(lane.getId());
				processDiagramCanvas
						.drawPoolOrLane(lane.getName(), graphicInfo);
			}
		}
		
		for (Process process : bpmnModel.getProcesses()) {
			for (FlowNode flowNode : process
					.findFlowElementsOfType(FlowNode.class)) {
				drawActivity(processDiagramCanvas, bpmnModel, flowNode,
						highLightedActivities, highLightedFlows, scaleFactor);
			}
		}
		for (Process process : bpmnModel.getProcesses()) {

			for (Artifact artifact : process.getArtifacts()) {
				drawArtifact(processDiagramCanvas, bpmnModel, artifact);
			}

			List<SubProcess> subProcesses = process.findFlowElementsOfType(
					SubProcess.class, true);
			if (subProcesses != null) {
				for (SubProcess subProcess : subProcesses) {
					for (Artifact subProcessArtifact : subProcess
							.getArtifacts()) {
						drawArtifact(processDiagramCanvas, bpmnModel,
								subProcessArtifact);
					}
				}
			}
		}
		return processDiagramCanvas;
	}
	protected static DefaultProcessDiagramCanvas initProcessDiagramCanvas(
            BpmnModel bpmnModel, String imageType, String activityFontName,
            String labelFontName, String annotationFontName,
            ClassLoader customClassLoader) {
		double minX = Double.MAX_VALUE;
		double maxX = 0;
		double minY = Double.MAX_VALUE;
		double maxY = 0;
		for (Pool pool : bpmnModel.getPools()) {
			GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(pool.getId());
			minX = graphicInfo.getX();
			maxX = graphicInfo.getX() + graphicInfo.getWidth();
			minY = graphicInfo.getY();
			maxY = graphicInfo.getY() + graphicInfo.getHeight();
		}
		List<FlowNode> flowNodes = gatherAllFlowNodes(bpmnModel);
		for (FlowNode flowNode : flowNodes) {

			GraphicInfo flowNodeGraphicInfo = bpmnModel.getGraphicInfo(flowNode
					.getId());
			if (flowNodeGraphicInfo.getX() + flowNodeGraphicInfo.getWidth() > maxX) {
				maxX = flowNodeGraphicInfo.getX()
						+ flowNodeGraphicInfo.getWidth();
			}
			if (flowNodeGraphicInfo.getX() < minX) {
				minX = flowNodeGraphicInfo.getX();
			}
			if (flowNodeGraphicInfo.getY() + flowNodeGraphicInfo.getHeight() > maxY) {
				maxY = flowNodeGraphicInfo.getY()
						+ flowNodeGraphicInfo.getHeight();
			}
			if (flowNodeGraphicInfo.getY() < minY) {
				minY = flowNodeGraphicInfo.getY();
			}

			for (SequenceFlow sequenceFlow : flowNode.getOutgoingFlows()) {
				List<GraphicInfo> graphicInfoList = bpmnModel
						.getFlowLocationGraphicInfo(sequenceFlow.getId());
				if (graphicInfoList != null) {
					for (GraphicInfo graphicInfo : graphicInfoList) {
						if (graphicInfo.getX() > maxX) {
							maxX = graphicInfo.getX();
						}
						if (graphicInfo.getX() < minX) {
							minX = graphicInfo.getX();
						}
						if (graphicInfo.getY() > maxY) {
							maxY = graphicInfo.getY();
						}
						if (graphicInfo.getY() < minY) {
							minY = graphicInfo.getY();
						}
					}
				}
			}
		}

		List<Artifact> artifacts = gatherAllArtifacts(bpmnModel);
		for (Artifact artifact : artifacts) {

			GraphicInfo artifactGraphicInfo = bpmnModel.getGraphicInfo(artifact
					.getId());

			if (artifactGraphicInfo != null) {
				if (artifactGraphicInfo.getX() + artifactGraphicInfo.getWidth() > maxX) {
					maxX = artifactGraphicInfo.getX()
							+ artifactGraphicInfo.getWidth();
				}
				if (artifactGraphicInfo.getX() < minX) {
					minX = artifactGraphicInfo.getX();
				}
				if (artifactGraphicInfo.getY()
						+ artifactGraphicInfo.getHeight() > maxY) {
					maxY = artifactGraphicInfo.getY()
							+ artifactGraphicInfo.getHeight();
				}
				if (artifactGraphicInfo.getY() < minY) {
					minY = artifactGraphicInfo.getY();
				}
			}

			List<GraphicInfo> graphicInfoList = bpmnModel
					.getFlowLocationGraphicInfo(artifact.getId());
			if (graphicInfoList != null) {
				for (GraphicInfo graphicInfo : graphicInfoList) {
					if (graphicInfo.getX() > maxX) {
						maxX = graphicInfo.getX();
					}
					if (graphicInfo.getX() < minX) {
						minX = graphicInfo.getX();
					}
					if (graphicInfo.getY() > maxY) {
						maxY = graphicInfo.getY();
					}
					if (graphicInfo.getY() < minY) {
						minY = graphicInfo.getY();
					}
				}
			}
		}

		int nrOfLanes = 0;
		for (Process process : bpmnModel.getProcesses()) {
			for (Lane l : process.getLanes()) {
				nrOfLanes++;
				GraphicInfo graphicInfo = bpmnModel.getGraphicInfo(l.getId());
				if (graphicInfo.getX() + graphicInfo.getWidth() > maxX) {
					maxX = graphicInfo.getX() + graphicInfo.getWidth();
				}
				if (graphicInfo.getX() < minX) {
					minX = graphicInfo.getX();
				}
				// height
				if (graphicInfo.getY() + graphicInfo.getHeight() > maxY) {
					maxY = graphicInfo.getY() + graphicInfo.getHeight();
				}
				if (graphicInfo.getY() < minY) {
					minY = graphicInfo.getY();
				}
			}
		}

		if (flowNodes.isEmpty() && bpmnModel.getPools().isEmpty()
				&& nrOfLanes == 0) {
			minX = 0;
			minY = 0;
		}

		return new ProcessDiagramCanvas((int) maxX + 10, (int) maxY + 10,
				(int) minX, (int) minY, imageType);
	}
}
