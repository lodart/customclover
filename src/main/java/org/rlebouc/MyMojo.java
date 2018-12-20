package org.rlebouc;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.atlassian.clover.CloverDatabase;
import com.atlassian.clover.CoverageDataSpec;
import com.atlassian.clover.api.CloverException;
import com.atlassian.clover.api.registry.*;
import com.atlassian.clover.registry.entities.FullProjectInfo;
import com.atlassian.clover.registry.metrics.HasMetricsFilter;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * 
 * @phase process-sources
 */
@Mojo( name = "readdb", requiresProject = true, aggregator = true) //aggregator means only on parent project
public class MyMojo extends AbstractMojo
{

    String dbPath = "/home/romain/workspace/MultiModuleProject/target/clover/cloverMerge.db";
    public void execute() throws MojoExecutionException
    {
        CloverDatabase db;
        try {
            db = CloverDatabase.loadWithCoverage(dbPath, new CoverageDataSpec());
        } catch (CloverException e) {
            getLog().info(e.getMessage());
            return;
        }

        FullProjectInfo fullModel = db.getFullModel();

        for (PackageInfo aPackage : fullModel.getAllPackages()) {
            for (ClassInfo aClass : aPackage.getAllClasses()) {
                for (MethodInfo method : aClass.getMethods()) {
                    getLog().info("Method : "+method.getQualifiedName());
                }
            }
        }
    }
}
