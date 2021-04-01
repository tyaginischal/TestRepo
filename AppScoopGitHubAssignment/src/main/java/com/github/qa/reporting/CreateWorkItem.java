package com.github.qa.reporting;

/*import com.microsoft.tfs.core.TFSTeamProjectCollection;
import com.microsoft.tfs.core.clients.workitem.CoreFieldReferenceNames;
import com.microsoft.tfs.core.clients.workitem.WorkItem;
import com.microsoft.tfs.core.clients.workitem.WorkItemClient;
import com.microsoft.tfs.core.clients.workitem.project.Project;
import com.microsoft.tfs.core.clients.workitem.project.ProjectCollection;
import com.microsoft.tfs.core.clients.workitem.wittype.WorkItemType;
*/
public class CreateWorkItem
{
    
//	public static void main(final String[] args)
//    {
//       
//    	
//    	
//    	TFSTeamProjectCollection tpc = new TFSTeamProjectCollection(serverUrl, username, domain, password);
//		
//    	ProjectCollection coll = tpc.getWorkItemClient().getProjects();		
//    	Project project = coll.get("SebreaChamados_Teste");  
//    	        
//    	WorkItemClient workItemClient = project.getWorkItemClient();
//    	
//    	 // Find the work item type matching the specified name.
//        WorkItemType bugWorkItemType = project.getWorkItemTypes().get("Bug");
//
//        // Create a new work item of the specified type.
//        WorkItem newWorkItem = project.getWorkItemClient().newWorkItem(bugWorkItemType);
//
//        // Set the title on the work item.
//        newWorkItem.setTitle("Example Work Item");
//
//        // Add a comment as part of the change
//        newWorkItem.getFields().getField(CoreFieldReferenceNames.HISTORY).setValue(
//            "<p>Created automatically by a sample</p>");
//
//        // Save the new work item to the server.
//        newWorkItem.save();
//
//        System.out.println("Work item " + newWorkItem.getID() + " successfully created");
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    	
////    	TFSTeamProjectCollection tpc =
////            new TFSTeamProjectCollection(
////                SnippetSettings.COLLECTION_URL,
////                SnippetSettings.USERNAME,
////                SnippetSettings.DOMAIN,
////                SnippetSettings.PASSWORD,
////                SnippetSettings.HTTP_PROXY_URL,
////                SnippetSettings.HTTP_PROXY_USERNAME,
////                SnippetSettings.HTTP_PROXY_PASSWORD);
////
////        Project project = tpc.getWorkItemClient().getProjects().get(SnippetSettings.Test);
////
////        // Find the work item type matching the specified name.
////        WorkItemType bugWorkItemType = project.getWorkItemTypes().get("Bug");
////
////        // Create a new work item of the specified type.
////        WorkItem newWorkItem = project.getWorkItemClient().newWorkItem(bugWorkItemType);
////
////        // Set the title on the work item.
////        newWorkItem.setTitle("Example Work Item");
////
////        // Add a comment as part of the change
////        newWorkItem.getFields().getField(CoreFieldReferenceNames.HISTORY).setValue(
////            "<p>Created automatically by a sample</p>");
////
////        // Save the new work item to the server.
////        newWorkItem.save();
////
////        System.out.println("Work item " + newWorkItem.getID() + " successfully created");
//    }
}