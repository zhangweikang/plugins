package ${basePackage}.facade.impl;

import ${basePackage}.service.I${modelNameUpperCamel}Service;
import ${basePackage}.facade.I${modelNameUpperCamel}Facade;
import hbec.platform.commons.annotations.HbecFieldInit;
import hbec.platform.commons.services.ILoggingService;

/**
* I${modelNameUpperCamel}FacadeImpl接口实现
* Please insert todo ......
* Created by ${author} on ${date}.
*/
public class ${modelNameUpperCamel}FacadeImpl implements I${modelNameUpperCamel}Facade {

    @HbecFieldInit
    private I${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;
    @HbecFieldInit
    private ILoggingService logger;

    //TODO: ${date}
}