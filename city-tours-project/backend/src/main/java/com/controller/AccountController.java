package com.controller;

import java.util.List;
import javax.validation.Valid;

import com.authentication.AuthProvider;
import com.authentication.JwtTokenHandler;
import com.authentication.UnauthorizedException;
import com.authentication.UserCreationException;
import com.model.Landmark;
import com.model.LandmarkDao;
import com.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AccountController
 */
@RestController
public class AccountController {
    @Autowired
    private AuthProvider auth;

    @Autowired
    private JwtTokenHandler tokenHandler;
    
    @Autowired
	private LandmarkDao landmarkDao;

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestBody User user, RedirectAttributes flash) throws UnauthorizedException {
        if (auth.signIn(user.getUsername(), user.getPassword())) {
            User currentUser = auth.getCurrentUser();
            return tokenHandler.createToken(user.getUsername(), currentUser.getRole());
        } else {
            throw new UnauthorizedException();
        }
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@Valid @RequestBody User user, BindingResult result) throws UserCreationException {
        if (result.hasErrors()) {
            String errorMessages = "";
            for (ObjectError error : result.getAllErrors()) {
                errorMessages += error.getDefaultMessage() + "\n";
            }
            throw new UserCreationException(errorMessages);
        }
        auth.register(user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(),
        				user.getCategory1(), user.getCategory2(), user.getNeighborhood1(), user.getNeighborhood2());
        return "{\"success\":true}";
    }
    
    @RequestMapping(path = "/search", method = RequestMethod.GET)
	public @ResponseBody List<Landmark> landmark() {
		
		List<Landmark> landmarks = landmarkDao.getAllLandmarks();
		
		return landmarks;
	}

}