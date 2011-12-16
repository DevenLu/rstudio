/*
 * CheckoutBranchToolbarButton.java
 *
 * Copyright (C) 2009-11 by RStudio, Inc.
 *
 * This program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */
package org.rstudio.studio.client.workbench.views.vcs;

import org.rstudio.studio.client.common.SimpleRequestCallback;
import org.rstudio.studio.client.common.console.ConsoleProcess;
import org.rstudio.studio.client.common.vcs.GitServerOperations;
import org.rstudio.studio.client.workbench.views.vcs.common.ConsoleProgressDialog;
import org.rstudio.studio.client.workbench.views.vcs.common.events.VcsRefreshEvent;
import org.rstudio.studio.client.workbench.views.vcs.common.events.VcsRefreshHandler;
import org.rstudio.studio.client.workbench.views.vcs.git.model.GitState;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.inject.Inject;

public class CheckoutBranchToolbarButton extends BranchToolbarButton
{
   @Inject
   public CheckoutBranchToolbarButton(final GitState vcsState,
                                      final GitServerOperations server)
   {
      super(vcsState);
      
      vcsState.bindRefreshHandler(this, new VcsRefreshHandler()
      {
         @Override
         public void onVcsRefresh(VcsRefreshEvent event)
         {
            setBranchCaption(vcsState.getBranchInfo().getActiveBranch());
         }
      });
      
      addValueChangeHandler(new ValueChangeHandler<String>() {

         @Override
         public void onValueChange(ValueChangeEvent<String> event)
         {
            final String branch = event.getValue();
            server.gitCheckout(
                  branch,
                  new SimpleRequestCallback<ConsoleProcess>()
                  {
                     @Override
                     public void onResponseReceived(ConsoleProcess proc)
                     {
                        new ConsoleProgressDialog(proc,
                                                  server).showModal();
                     }
                  });
            
         }
         
      });
      
   }

}
