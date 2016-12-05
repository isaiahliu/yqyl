package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.FavoriteDto;
import org.trinity.yqyl.common.message.dto.domain.FavoriteSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IFavoriteProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IFavoriteRepository;
import org.trinity.yqyl.repository.business.entity.Favorite;

@Service
public class FavoriteProcessController
        extends AbstractAutowiredCrudProcessController<Favorite, FavoriteDto, FavoriteSearchingDto, IFavoriteRepository>
        implements IFavoriteProcessController {
}
