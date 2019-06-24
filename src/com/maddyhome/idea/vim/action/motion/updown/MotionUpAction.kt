/*
 * IdeaVim - Vim emulator for IDEs based on the IntelliJ platform
 * Copyright (C) 2003-2019 The IdeaVim authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.maddyhome.idea.vim.action.motion.updown;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.maddyhome.idea.vim.VimPlugin;
import com.maddyhome.idea.vim.action.motion.MotionEditorAction;
import com.maddyhome.idea.vim.command.Argument;
import com.maddyhome.idea.vim.command.Command;
import com.maddyhome.idea.vim.handler.MotionActionHandler;
import com.maddyhome.idea.vim.helper.CaretDataKt;
import com.maddyhome.idea.vim.helper.EditorHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class MotionUpAction extends MotionEditorAction {
  public MotionUpAction() {
    super(new Handler());
  }

  private static class Handler extends MotionActionHandler.ForEachCaret {
    @Override
    public int getOffset(@NotNull Editor editor, @NotNull Caret caret, @NotNull DataContext context, int count,
                         int rawCount, @Nullable Argument argument) {
      return VimPlugin.getMotion().moveCaretVertical(editor, caret, -count);
    }

    @Override
    public boolean preOffsetComputation(@NotNull Editor editor,
                                           @NotNull Caret caret,
                                           @NotNull DataContext context,
                                           @NotNull Command cmd) {
      col = CaretDataKt.getVimLastColumn(caret);
      return true;
    }

    @Override
    public void postMove(@NotNull Editor editor, @NotNull Caret caret, @NotNull DataContext context,
                            @NotNull Command cmd) {
      EditorHelper.updateLastColumn(editor, caret, col);
    }

    private int col;
  }
}