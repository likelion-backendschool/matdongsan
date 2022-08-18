package com.matdongsan.web.controller.favorite;

import com.matdongsan.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class FavoriteController {
    private final FavoriteService favoriteService;
}
