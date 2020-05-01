package net.mffjam2.common.gem;

import net.mffjam2.setup.JamGemProperties;

public enum ShootType
{
    SINGLE,
    MULTISHOT,
    BURST,
    SCATTERSHOT;
	
	private GemProperty property;
	
	public GemProperty asProperty()
	{
		if (property == null)
		{
			property = JamGemProperties.getPROPERTIES().get("shoot_type_" + this.toString().toLowerCase());
		}
		return property;
	}
}
