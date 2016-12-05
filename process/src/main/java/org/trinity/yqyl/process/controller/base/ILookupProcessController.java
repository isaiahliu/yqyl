package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.common.dto.object.LookupDto;
import org.trinity.process.controller.IProcessController;

public interface ILookupProcessController extends IProcessController {
    List<LookupDto> getLookupsByType(String lookupType);

    void refresh();
}
