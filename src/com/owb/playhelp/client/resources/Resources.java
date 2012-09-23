package com.owb.playhelp.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.core.client.GWT;

public interface Resources extends ClientBundle {
	// I added this line below
    public static final Resources INSTANCE =  GWT.create(Resources.class);

    @Source("ProfilePanel.gif")
    ImageResource profilePanel();
    @Source("SocialIcons.gif")
    ImageResource socialIcons();
    @Source("edit_profile.png")
    ImageResource sampleProfilePicture();
    @Source("loggedout.gif")
    ImageResource loggedoutPicture();
    @Source("AvatarPanel.gif")
    ImageResource avatarPanel();

    @Source("NewsHomePanel.jpg")
    ImageResource NewsHomePanel();
    @Source("FriendsHomePanel.jpg")
    ImageResource FriendsHomePanel();
    @Source("RadioHomePanel.jpg")
    ImageResource RadioHomePanel();
    
    @Source("owb_logo_verysmall.gif")
    ImageResource owbLogo();
    @Source("Name2Logo.gif")
    ImageResource name2Logo();
    
    @Source("emailTop.gif")
    ImageResource emailLogo();
    @Source("newsTop.gif")
    ImageResource newsLogo();
    @Source("worldTop.gif")
    ImageResource worldLogo();
    @Source("friendTop.gif")
    ImageResource friendLogo();
    @Source("blogTop.gif")
    ImageResource blogLogo();
    @Source("radioTop.gif")
    ImageResource radioLogo();

    @Source("emailSelTop.gif")
    ImageResource emailSelLogo();
    @Source("newsSelTop.gif")
    ImageResource newsSelLogo();
    @Source("worldSelTop.gif")
    ImageResource worldSelLogo();
    @Source("friendSelTop.gif")
    ImageResource friendSelLogo();
    @Source("blogSelTop.gif")
    ImageResource blogSelLogo();
    @Source("radioSelTop.gif")
    ImageResource radioSelLogo();
    

	@Source("propertyButton.png")
	ImageResource propertyButton();
    
    // Map images
    @Source("NgoMapIcon2.gif")
    ImageResource ngoMapIcon();
    @Source("NgoMapIcon.gif")
    ImageResource orphanageMapIcon();
    
}
