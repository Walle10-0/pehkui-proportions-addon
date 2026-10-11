package com.walle.pehkuiproportions;

import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleModifier;
import virtuoel.pehkui.api.ScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.function.Supplier;

public class CompoundScaleModifier extends ScaleModifier {

    private final List<Entry> entries;

    /**
     * Internal immutable entry type
     */
    public static class Entry {
        public final Supplier<ScaleType> type;

        public Entry(Supplier<ScaleType> type) {
            this.type = type;
        }
    }

	/**
     * Varargs constructor
     */
    @SafeVarargs
    public CompoundScaleModifier(Entry... entries) {
        List<Entry> temp = new ArrayList<>();

        for (Entry entry : entries) {
            temp.add(entry);
        }

        this.entries = Collections.unmodifiableList(temp);
    }
	
	@Override
	public float modifyScale(final ScaleData scaleData, float modifiedScale, final float delta)
	{	
		return compute(scaleData, modifiedScale, false, delta);
	}
	
	@Override
	public float modifyPrevScale(final ScaleData scaleData, float modifiedScale)
	{
		return compute(scaleData, modifiedScale, true, 0f);
	}

    private float compute(ScaleData data, float base, boolean prev, float delta) {

        float result = 1f;
        var entity = data.getEntity();

        for (Entry entry : entries) {

            ScaleType type = entry.type.get();
            ScaleData other = type.getScaleData(entity);

            float value = prev
                ? other.getPrevScale()
                : other.getScale(delta);

            result *= value;
        }

        return result;
    }
}
