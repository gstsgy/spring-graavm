<#-- 流水查询 -->
<#macro transactions>
    select s.*, o.nick_name as user_name, a.`name` as account_name
    from transactions s
    left join operator o on s.user_id = o.id and o.effective=1
    left join account_book a on s.account_id = a.id and a.effective=1
    where 1=1
    and s.effective = 1
    and s.account_id = :accountId

<#-- 参数校验建议加上 != "" 防止空字符串干扰 -->
    <#if userId?? && userId != "">  and s.user_id = :userId </#if>
    <#if desc?? && desc != "">     AND s.desc LIKE CONCAT('%', :desc, '%') </#if>
    <#if model?? && model != "">   AND s.model = :model </#if>
    <#if startDate?? && startDate != ""> and s.time >= :startDate </#if>
    <#if endDate?? && endDate != "">     and s.time <= :endDate </#if>

<#-- 如果使用 NamedParameterJdbcTemplate，直接用 (:types) 最方便 -->
    <#if types?? && types != "">
        AND s.type IN (
        <#list types?split(",") as t>
            '${t}'<#if t_has_next>,</#if>
        </#list>
        )
    </#if>

    order by s.time desc, s.user_id, s.insert_ymd desc
</#macro>
