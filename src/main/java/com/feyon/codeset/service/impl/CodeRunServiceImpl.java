package com.feyon.codeset.service.impl;

import cn.hutool.core.util.ClassUtil;
import com.feyon.codeset.dto.CodeRunDTO;
import com.feyon.codeset.service.CodeRunService;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Feng Yong
 */
@Service
public class CodeRunServiceImpl implements CodeRunService {
    @Override
    public CodeRunDTO run(Integer languageId, String code) {
        CodeRunDTO dto = new CodeRunDTO();
        dto.setResult(1);
        dto.setMemoryCost(1000);
        dto.setTimeCost(1000);
        return dto;
    }

    public boolean runCode(String code) {

        return true;
    }
}
