/*
 * HistoryBranchToolbarButton.java
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

import org.rstudio.studio.client.workbench.views.vcs.common.events.VcsRefreshEvent;
import org.rstudio.studio.client.workbench.views.vcs.common.events.VcsRefreshHandler;
import org.rstudio.studio.client.workbench.views.vcs.git.model.GitState;

import com.google.inject.Inject;

public class HistoryBranchToolbarButton extends BranchToolbarButton
{
   @Inject
   public HistoryBranchToolbarButton(final GitState vcsState)
   {
      super(vcsState);
      
      vcsState.bindRefreshHandler(this, new VcsRefreshHandler()
      {
         @Override
         public void onVcsRefresh(VcsRefreshEvent event)
         {
            // one time initialization of our caption (need to do it here
            // because vcsState.getBranchInfo is null when we are created)
            if (!initialized_)
            {
               initialized_ = true;
               setBranchCaption(vcsState.getBranchInfo().getActiveBranch());
            }
         }
         
         boolean initialized_ = false;
      });
      
   }

}
