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
package raptor.game;

import static raptor.game.util.GameUtils.bitscanClear;
import static raptor.game.util.GameUtils.bitscanForward;

public class CrazyhouseGame extends Game {
	public CrazyhouseGame() {
		setType(Type.CRAZYHOUSE);
		addState(Game.DROPPABLE_STATE);
	}

	/**
	 * @param ignoreHashes
	 *            Whether to include copying hash tables.
	 * @return An deep clone copy of this Game object.
	 */
	@Override
	public Game deepCopy(boolean ignoreHashes) {
		CrazyhouseGame result = new CrazyhouseGame();
		overwrite(result, ignoreHashes);
		return result;
	}

	/**
	 * Generates all of the pseudo legal drop moves in the position and adds
	 * them to the specified move list.
	 * 
	 * @param moves
	 *            A move list.
	 */
	public void generatePseudoDropMoves(PriorityMoveList moves) {

		if (getDropCount(getColorToMove(), PAWN) > 0) {

			long emptyBB = getEmptyBB() & NOT_RANK1 & NOT_RANK8;
			while (emptyBB != 0) {
				int toSquare = bitscanForward(emptyBB);

				addMove(new Move(toSquare, PAWN, getColorToMove()), moves);
				emptyBB = bitscanClear(emptyBB);
			}
		}

		if (getDropCount(getColorToMove(), KNIGHT) > 0) {

			long emptyBB = getEmptyBB();
			while (emptyBB != 0) {
				int toSquare = bitscanForward(emptyBB);

				addMove(new Move(toSquare, KNIGHT, getColorToMove()), moves);
				emptyBB = bitscanClear(emptyBB);
			}
		}

		if (getDropCount(getColorToMove(), BISHOP) > 0) {

			long emptyBB = getEmptyBB();
			while (emptyBB != 0) {
				int toSquare = bitscanForward(emptyBB);

				addMove(new Move(toSquare, BISHOP, getColorToMove()), moves);
				emptyBB = bitscanClear(emptyBB);
			}
		}

		if (getDropCount(getColorToMove(), ROOK) > 0) {

			long emptyBB = getEmptyBB();
			while (emptyBB != 0) {
				int toSquare = bitscanForward(emptyBB);

				addMove(new Move(toSquare, ROOK, getColorToMove()), moves);
				emptyBB = bitscanClear(emptyBB);
			}
		}

		if (getDropCount(getColorToMove(), QUEEN) > 0) {

			long emptyBB = getEmptyBB();
			while (emptyBB != 0) {
				int toSquare = bitscanForward(emptyBB);

				addMove(new Move(toSquare, QUEEN, getColorToMove()), moves);
				emptyBB = bitscanClear(emptyBB);
			}
		}
	}

	/**
	 * Overridden to invoke genDropMoves as well as super.getPseudoLegalMoves.
	 */
	@Override
	public PriorityMoveList getPseudoLegalMoves() {
		PriorityMoveList result = super.getPseudoLegalMoves();
		generatePseudoDropMoves(result);
		return result;
	}

	/**
	 * Returns a dump of the game class suitable for debugging. Quite a lot of
	 * information is produced and its an expensive operation, use with care.
	 */
	@Override
	public String toString() {
		return super.toString() + "\n" + getDropCountsString();
	}

}
