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
package raptor.pref.page;

import org.apache.commons.lang.WordUtils;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import raptor.Raptor;
import raptor.pref.PreferenceKeys;
import raptor.pref.fields.LabelButtonFieldEditor;
import raptor.pref.fields.LabelFieldEditor;
import raptor.service.SoundService;

public class SoundPage extends FieldEditorPreferencePage {
	LabelButtonFieldEditor labelButtonFieldEditor;

	public SoundPage() {
		super(FLAT);
		setTitle("Sound");
		setPreferenceStore(Raptor.getInstance().getPreferences());
	}

	@Override
	protected void createFieldEditors() {
		LabelFieldEditor userHomeDir = new LabelFieldEditor(
				"NONE",
				WordUtils
						.wrap(
								"Raptor has two different ways of playing sound.One which is used in Mac OS X and Windows, and "
										+ "another one for Linux and Solaris.However, some linux/Solaris "
										+ "users might run into problems mixing sounds with other applications. If this is the "
										+ "case they can configure a process to use for sounds , play or aoss for instance, instead "
										+ "of using the java sound apis. To remove the process setting just delete the process name.",
								70), getFieldEditorParent());
		addField(userHomeDir);

		final StringFieldEditor soundProcessName = new StringFieldEditor(
				PreferenceKeys.SOUND_PROCESS_NAME, "Sound process name:",
				getFieldEditorParent());
		addField(soundProcessName);

		labelButtonFieldEditor = new LabelButtonFieldEditor(
				"NONE",
				"You can use this button to test the setting (Requires an apply): ",
				getFieldEditorParent(), "Test", new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						SoundService.getInstance().initSoundPlayer();
						SoundService.getInstance().playSound("win");
					}
				});
		addField(labelButtonFieldEditor);
	}
}