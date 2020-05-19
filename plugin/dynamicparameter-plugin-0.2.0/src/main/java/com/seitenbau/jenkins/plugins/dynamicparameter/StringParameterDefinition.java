/*
 * Copyright 2012 Seitenbau
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seitenbau.jenkins.plugins.dynamicparameter;

import hudson.Extension;
import hudson.model.ParameterValue;
import hudson.model.StringParameterValue;

import org.jvnet.localizer.ResourceBundleHolder;
import org.kohsuke.stapler.DataBoundConstructor;

/** Text parameter, with dynamically generated default value. */
public class StringParameterDefinition extends ScriptParameterDefinition
{
  /** Serial version UID. */
  private static final long serialVersionUID = 3162331168133114084L;
  
  private final boolean readonlyInputField;

  /**
   * Constructor is deprecated use instead the constructor with the read only parameter.
   * Only some unit tests should depend on this constructor.
   * 
   * Will be removed in the future.
   */
  @Deprecated
  public StringParameterDefinition(String name, String script, String description, String uuid,
      boolean remote, String classPath)
  {
    this(name, script, description, uuid, remote, false, classPath);
  }
  
  /**
   * Constructor with the parameter which are injected by the jenkins runtime.
   * 
   * @param name parameter name
   * @param script script, which generates the parameter value
   * @param description parameter description
   * @param uuid identifier (optional)
   * @param remote execute the script on a remote node
   * @param readonlyInputField should the input field marked as read only true / false
   * @param classPath the class path description
   */
  @DataBoundConstructor
  public StringParameterDefinition(String name, String script, String description, String uuid,
      boolean remote, boolean readonlyInputField, String classPath)
  {
    super(name, script, description, uuid, remote, classPath);
    this.readonlyInputField = readonlyInputField;
  }
  

  /**
   * Execute the script and return the default value for this parameter.
   * @return the default value generated by the script or {@code null}
   */
  public final String getDefaultValue()
  {
    return getScriptResultAsString();
  }
  
  /**
   * Return default parameter value - used by trigger mechanism.
   */
  @Override
  public ParameterValue getDefaultParameterValue() {
     StringParameterValue stringParameterValue = new StringParameterValue(getName(), getDefaultValue());
     return stringParameterValue;
  }

  public final boolean isReadonlyInputField() 
  {
    return readonlyInputField;
  }

  /** Parameter descriptor. */
  @Extension
  public static final class DescriptorImpl extends BaseDescriptor
  {
    private static final String DISPLAY_NAME = "DisplayName";

    @Override
    public final String getDisplayName()
    {
      return ResourceBundleHolder.get(StringParameterDefinition.class).format(DISPLAY_NAME);
    }
  }
  
}