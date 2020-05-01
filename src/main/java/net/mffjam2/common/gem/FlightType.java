package net.mffjam2.common.gem;

import net.mffjam2.setup.JamGemProperties;

public enum FlightType
{
    NORMAL,
    HOMING,
    NO_CLIP,
    BOUNCE;
	
	private GemProperty property;
	
	public GemProperty asProperty()
	{
		if (property == null)
		{
			property = JamGemProperties.getPROPERTIES().get("flight_type_" + this.toString().toLowerCase());
		}
		return property;
	}
}
