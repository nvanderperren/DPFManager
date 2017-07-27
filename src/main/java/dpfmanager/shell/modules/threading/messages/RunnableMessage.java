/**
 * <h1>RunnableMessage.java</h1> <p> This program is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any later version; or,
 * at your choice, under the terms of the Mozilla Public License, v. 2.0. SPDX GPL-3.0+ or MPL-2.0+.
 * </p> <p> This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE. See the GNU General Public License and the Mozilla Public License for more details. </p>
 * <p> You should have received a copy of the GNU General Public License and the Mozilla Public
 * License along with this program. If not, see <a href="http://www.gnu.org/licenses/">http://www.gnu.org/licenses/</a>
 * and at <a href="http://mozilla.org/MPL/2.0">http://mozilla.org/MPL/2.0</a> . </p> <p> NB: for the
 * © statement, include Easy Innova SL or other company/Person contributing the code. </p> <p> ©
 * 2015 Easy Innova, SL </p>
 *
 * @author Adria Llorens
 * @version 1.0
 * @since 23/7/2015
 */

package dpfmanager.shell.modules.threading.messages;

import dpfmanager.shell.core.messages.DpfMessage;
import dpfmanager.shell.modules.threading.runnable.DpfRunnable;

/**
 * Created by Adria Llorens on 13/04/2016.
 */
public class RunnableMessage extends DpfMessage {

  private Long uuid;
  private String pool;
  private DpfRunnable runnable;

  public RunnableMessage(Long u, DpfRunnable r) {
    uuid = u;
    runnable = r;
    pool = "default";
  }

  public RunnableMessage(Long u, DpfRunnable r, String p) {
    uuid = u;
    runnable = r;
    pool = p;
  }

  public DpfRunnable getRunnable() {
    return runnable;
  }

  public Long getUuid() {
    return uuid;
  }

  public String getPool() {
    return pool;
  }
}
