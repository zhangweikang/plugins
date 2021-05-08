package ${basePackage}.service.impl;

import ${basePackage}.bean.${modelNameUpperCamel};
import ${basePackage}.repository.I${modelNameUpperCamel}Repository;
import ${basePackage}.service.I${modelNameUpperCamel}Service;
import hbec.app.acnt.server.services.common.AcntBaseService;
import hbec.platform.commons.services.ILoggingService;

/**
* I${modelNameUpperCamel}ServiceImpl接口实现
* Please insert todo ......
* Created by ${author} on ${date}.
*/
public class ${modelNameUpperCamel}ServiceImpl extends AcntBaseService<${modelNameUpperCamel}> implements I${modelNameUpperCamel}Service {

    private I${modelNameUpperCamel}Repository ${modelNameLowerCamel}Repository;
    private ILoggingService logger;

    public ${modelNameUpperCamel}ServiceImpl(I${modelNameUpperCamel}Repository ${modelNameLowerCamel}Repository,ILoggingService logger) {
        super(${modelNameLowerCamel}Repository);
        this.${modelNameLowerCamel}Repository = ${modelNameLowerCamel}Repository;
        this.logger = logger;
    }

    //TODO: ${date}
}
