/**
 * New BSD License
 * http://www.opensource.org/licenses/bsd-license.php
 * Copyright (c) 2009, RaptorProject (http://code.google.com/p/raptor-chess-interface/)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 * Neither the name of the RaptorProject nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package raptor.swt.chess.movelist;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import raptor.chess.Game;
import raptor.chess.pgn.PgnHeader;
import raptor.chess.util.GameUtils;
import raptor.swt.chess.ChessBoardController;
import raptor.swt.chess.ChessBoardMoveList;

/**
 * Simple movelist that uses SWT StyledText. This would allow to support
 * annotation and variation trees within the widget.
 * 
 * TODO add MouseListener to support choosing a move
 * TODO allow annotations
 */
public class TextAreaMoveList implements ChessBoardMoveList {
	private static final Log LOG = LogFactory.getLog(SimpleMoveList.class);
	
	protected ChessBoardController controller;
	protected StyledText textPanel;
	
	/**
	 * Defines offsets for each move node, where the list index is the move number
	 */
	protected List<Integer> moveNodes;
	
	/**
	 * Defines lengths (in chars) for each move node
	 */
	protected List<Integer> moveNodesLengths;
	
	/**
	 * Offset where the header ends
	 */
	protected int movesTextStart;
	protected Color moveSelectionColor;
	private int selectedHalfmove;

	/**
	 * {@inheritDoc}
	 */
	public void clear() {
		textPanel.replaceTextRange(0, textPanel.getCharCount(), "");
		moveNodes.clear();
		moveNodesLengths.clear();
	}

	/**
	 * {@inheritDoc}
	 */
	public Composite create(Composite parent) {
		if (textPanel == null) {
			createControls(parent);
		}
		return textPanel;		
	}

	private void createControls(Composite parent) {		
		textPanel = new StyledText(parent, SWT.BORDER | SWT.V_SCROLL);
		textPanel.setEditable(false);
		textPanel.setWordWrap(true);
		moveSelectionColor = new Color(Display.getCurrent(), 165, 192, 255);
		moveNodes = new ArrayList<Integer>();
		moveNodesLengths = new ArrayList<Integer>();
		textPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int caretOffset = textPanel.getCaretOffset();
				int count = 0;
				for (int nodeOffset: moveNodes) {
					if (nodeOffset > caretOffset) {
						controller.userSelectedMoveListMove(count-1);
						select(count-1);
						break;
					}
					count++;	
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	public void forceRedraw() {
		updateToGame();
	}

	/**
	 * {@inheritDoc}
	 */
	public ChessBoardController getChessBoardController() {
		return controller;
	}

	/**
	 * {@inheritDoc}
	 */
	public Composite getControl() {
		return textPanel;
	}

	/**
	 * {@inheritDoc}
	 */
	public void select(int halfMoveIndex) {
		if (halfMoveIndex >= moveNodes.size() || halfMoveIndex < 0
				|| selectedHalfmove == halfMoveIndex)
			return;

		StyleRange sR = new StyleRange();
	    sR.background = moveSelectionColor;
	    sR.start = moveNodes.get(halfMoveIndex);
	    sR.length = moveNodesLengths.get(halfMoveIndex);;
		textPanel.replaceStyleRanges(movesTextStart, textPanel.getCharCount()
				- movesTextStart, new StyleRange[] { sR });
		textPanel.setCaretOffset(sR.start+sR.length);
		selectedHalfmove = halfMoveIndex;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setController(ChessBoardController controller) {
		this.controller = controller;		
	}

	/**
	 * {@inheritDoc}
	 */
	public void updateToGame() {
		if (textPanel.isVisible()) {
			long startTime = System.currentTimeMillis();
		
			if (textPanel.getCharCount() == 0) {
				String sd = textPanel.getLineDelimiter();
				String title = controller.getGame().getHeader(PgnHeader.White)
						+ " vs " + controller.getGame().getHeader(PgnHeader.Black)
						+ sd + sd;
				textPanel.append(title);
				StyleRange styleRange = new StyleRange();
				styleRange.start = 0;
				styleRange.length = title.length();
				styleRange.fontStyle = SWT.BOLD;
				textPanel.setStyleRange(styleRange);
				movesTextStart = textPanel.getCharCount();
			}

			Game game = controller.getGame();
			int moveListSize = game.getMoveList().getSize();
			if (moveListSize == 0 && textPanel.getCharCount() != 0) {
				textPanel.replaceTextRange(movesTextStart, textPanel
						.getCharCount(), "");
			} else {
				if (moveListSize == moveNodes.size())
					return;

				StringBuffer buff = new StringBuffer();
				for (int i = moveNodes.size(); i < moveListSize; i++) {
					int start, length;
					start = textPanel.getCharCount() + buff.length();
					String move = getMoveNumber(i)
							+ GameUtils.convertSanToUseUnicode(game
									.getMoveList().get(i).toString(), true);
					buff.append(move);
					length = move.length();
					moveNodes.add(start);
					moveNodesLengths.add(length);
					buff.append(" ");
				}
				textPanel.append(buff.toString());
				select(moveListSize - 1);

			}
			
			if (LOG.isDebugEnabled()) {
				LOG.debug("Updated to game in : "
						+ (System.currentTimeMillis() - startTime));
			}
		}
		
	}

	protected void appendMove(int moveListSize) {
		textPanel.append(getMoveNumber(moveListSize)
				+ GameUtils.convertSanToUseUnicode(controller.getGame()
						.getMoveList().get(moveListSize).toString(), true));
		select(moveListSize-1);
	}
	
	private String getMoveNumber(int i) {
		return (i % 2 == 0) ? Integer.toString((i+3)/2) + "." : "";
	}
	
}
