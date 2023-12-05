/*
 * Copyright (C) 2014 The CyanogenMod Project
 * Copyright (C) 2017 AICP
 * Copyright (C) 2022 Project Kaleidoscope
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.custom.preference;

import android.content.Context;
import androidx.preference.SwitchPreference;
import android.util.AttributeSet;
import android.provider.Settings;

public class SystemPropertySwitchPreference extends SwitchPreference {

    public SystemPropertySwitchPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setPreferenceDataStore(new SystemPropertiesStore());
    }

    public SystemPropertySwitchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPreferenceDataStore(new SystemPropertiesStore());
    }

    public SystemPropertySwitchPreference(Context context) {
        super(context);
        setPreferenceDataStore(new SystemPropertiesStore());
    }

    protected boolean isPersisted() {
        return Settings.System.getString(getContext().getContentResolver(), getKey()) != null;
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        return Settings.System.getInt(getContext().getContentResolver(),
                key, defaultValue ? 1 : 0) != 0;
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        final boolean checked;
        if (!restorePersistedValue || !isPersisted()) {
            if (defaultValue == null) {
                return;
            }
            checked = (boolean) defaultValue;
            if (shouldPersist()) {
                persistBoolean(checked);
            }
        } else {
            // Note: the default is not used because to have got here
            // isPersisted() must be true.
            checked = getBoolean(getKey(), false /* not used */);
        }
        setChecked(checked);
    }
}
