package ${basePackage}.repository.impl;

import ${basePackage}.bean.${modelNameUpperCamel};
import ${basePackage}.repository.I${modelNameUpperCamel}Repository;
import hbec.app.acnt.server.repository.common.AcntBaseRepository;
import hbec.platform.commons.exceptions.HbecDbServiceException;
import hbec.platform.commons.services.IDbService;
import hbec.platform.commons.services.ILoggingService;

import java.text.MessageFormat;
import java.util.List;

/**
* I${modelNameUpperCamel}RepositoryImpl接口实现
* Please insert todo ......
* Created by ${author} on ${date}.
*/
public class ${modelNameUpperCamel}RepositoryImpl extends AcntBaseRepository<${modelNameUpperCamel}> implements I${modelNameUpperCamel}Repository {

    private IDbService dbService;
    private ILoggingService logger;

    public ${modelNameUpperCamel}RepositoryImpl(IDbService dbService, ILoggingService logger) {
        super(dbService, logger, ${modelNameUpperCamel}.class);
        this.dbService = dbService;
        this.logger = logger;
    }

    //TODO: ${date}

    /*//mybatis调用方式
    public List<${modelNameUpperCamel}> test(String args...) throws HbecDbServiceException {
        return dbService.mySelectList(MessageFormat.format("{0}Mapper.findByAccountPhone", entityClassName),new Object[args...]);
    }*/
}