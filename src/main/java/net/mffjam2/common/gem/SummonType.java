package net.mffjam2.common.gem;

import net.mffjam2.setup.JamGemProperties;

public enum SummonType
{
    POINT_SELF,
    POINT_TARGET,
    SKY_SELF,
    SKY_TARGET,
    AREA_SELF,
    AREA_TARGET;
	
	private GemProperty property;
	
	public GemProperty asProperty()
	{
		if (property == null)
		{
			property = JamGemProperties.getPROPERTIES().get("summon_type_" + this.toString().toLowerCase());
		}
		return property;
	}
}
