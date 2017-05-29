<#ftl strip_whitespace=true>
<#import "/spring.ftl" as spring />
 <#--
    A collection of macros for form inputs on administration screens, they are all styled using
    the default Bootstrap 3 input styles, with error messages appearing underneath said input.

    They are basically wrappers around everything provided by `spring.ftl`, just slightly updated to use different markup.
-->

<!-- A text input bound to "path", HtmlUnknownTag -->
<#macro textInput path label="No label entered..." className="" hint="">
    <div class="form-group">
        <@spring.bind path />
        <label for="${spring.status.expression}">
            ${label}
        </label>

        <#if hint??>
            <p class="help-text">${hint}</p>
        </#if>
        <input type="text"
               class="form-control ${className}"
               id="${spring.status.expression}"
               name="${spring.status.expression}"
               value="${spring.status.value!''}" />

        <@errors messages=spring.status.errorMessages />
    </div>
</#macro>

<!-- Renders BindingResult constraint violations, HtmlUnknownTag -->
<#macro errors messages>

    <#if messages?has_content>
        <div class="alert alert-danger">

            <#list messages as error>
                ${error}
            </#list>
        </div>
    </#if>
</#macro>

<!-- A text input bound to a path, with a button to select / upload an image, HtmlUnknownTag -->
<#macro imageInput path label="No label entered..." className="" hint="">
    <div class="form-group">
        <@spring.bind path />
        <label for="${spring.status.expression}">
            ${label}
        </label>

        <#if hint??>
            <p class="help-text">${hint}</p>
        </#if>

        <div class="input-group">
            <input type="text"
                   class="form-control ${className}"
                   placeholder=""
                   id="${spring.status.expression}"
                   name="${spring.status.expression}"
                   value="${spring.status.value!''}" />
            <span class="input-group-btn">
                <button class="js-image-select btn btn-default" type="button">Select image</button>
            </span>
        </div>
        <@errors messages=spring.status.errorMessages />
    </div>
</#macro>

<!-- A hidden input element bound to a path, HtmlUnknownTag -->
<#macro hiddenInput path>
    <@spring.bind path />
    <input type="hidden" name="${spring.status.expression}"
           value="${spring.status.value!''}" />
</#macro>

<!-- Radio buttons bound to a path, HtmlUnknownTag -->
<#macro radioButtons path options={} label="" hint="">
    <@spring.bind path />
    <div class="form-group">
        <label for="${spring.status.expression}">
            ${label}
        </label>

        <#if hint??>
            <p class="help-text">${hint}</p>
        </#if>

        <@spring.formRadioButtons path=path  options=options separator=" " />

        <@errors messages=spring.status.errorMessages />
    </div>
</#macro>

<!-- A select element bound to a path, HtmlUnknownTag -->
 <#macro select path options label="No label entered..." className="" hint="">
    <@spring.bind path />

    <label for="${spring.status.expression}">
        ${label}
    </label>

        <#if hint??>
        <p class="help-text">${hint}</p>
    </#if>

    <select id="${spring.status.expression?replace('[','')?replace(']','')}" name="${spring.status.expression}" class="form-control ${className}">

        <#if options?is_hash>

            <#list options?keys as value>
                <option value="${value?html}"<@spring.checkSelected value/>>${options[value]?html}</option>
            </#list>

            <#else>

                <#list options as value>
                    <option value="${value?html}"<@spring.checkSelected value/>>${value?html}</option>
                </#list>
        </#if>
    </select><br />
     <@errors messages=spring.status.errorMessages />
</#macro>

<!-- An input that has an AJAX merchant lookup attached, when you type in a store name or ID, it will present a list of results from the GAYL merchant API. -->
<#macro merchantLookupInput path label="No label entered..." className="" hint="">
    <div class="form-group">
        <@spring.bind path />
        <label for="${spring.status.expression}">
            ${label}
        </label>

        <#if hint??>
            <p class="help-text">${hint}</p>
        </#if>
        <input type="text"
               class="form-control js-merchant-lookup-input"
               id="${spring.status.expression}"
               name="${spring.status.expression}"
               value="${spring.status.value!''}" />

        <@errors messages=spring.status.errorMessages />
    </div>
</#macro>
