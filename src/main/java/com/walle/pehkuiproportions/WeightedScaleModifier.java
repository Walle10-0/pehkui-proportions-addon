package com.walle.pehkuiproportions;

import virtuoel.pehkui.api.ScaleData;
import virtuoel.pehkui.api.ScaleModifier;
import virtuoel.pehkui.api.ScaleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.function.Supplier;

public class WeightedScaleModifier extends ScaleModifier {

    private final List<Entry> entries;
    private final float totalWeight;

    /**
     * Internal immutable entry type
     */
    public static class Entry {
        public final Supplier<ScaleType> type;
        public final float weight;

        public Entry(Supplier<ScaleType> type, float weight) {
            this.type = type;
            this.weight = weight;
        }
    }

	/**
     * Varargs constructor
     */
    @SafeVarargs
    public WeightedScaleModifier(Entry... entries) {
        List<Entry> temp = new ArrayList<>();
        float sum = 0f;

        for (Entry entry : entries) {
            temp.add(entry);
            sum += entry.weight;
        }

        this.entries = Collections.unmodifiableList(temp);
        this.totalWeight = sum;
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

        float result = 0f;
        var entity = data.getEntity();

        for (Entry entry : entries) {

            ScaleType type = entry.type.get();
            ScaleData other = type.getScaleData(entity);

            float value = prev
                ? other.getPrevScale()
                : other.getScale(delta);

            float normalizedWeight = (totalWeight <= 0f)
                ? 0f
                : (entry.weight / totalWeight);

            result += normalizedWeight * value;
        }

        return result;
    }
}
