<#-- 流水查询 -->
<#macro transactions>
    select s.*, o.nick_name as user_name, a.`name` as account_name
    from transactions s
    left join operator o on s.user_id = o.id and o.is_del=0
    left join account_book a on s.account_id = a.id and a.is_del=0
    where 1=1
    and s.is_del = 0
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

<#-- 根据月度信息汇总查寻 -->
<#macro monthly>
    select left(time,7) as  month,round(sum(value),2)  as expense,0 as income from transactions s
    where
    is_del = 0
    and account_id = :accountId
    group by  left(time,7)
    order by month desc
</#macro>
<#-- 根据年度信息汇总查寻 -->
<#macro yearly>
    select left(time,4) as  year,round(sum(value),2)  as expense,0 as income from transactions s
    where
    is_del = 0
    and left(time,4) = :year
    and account_id = :accountId
    group by  left(time,4)

    order by year
</#macro>
<#-- 获取月度消费明细（按类型汇总） -->
<#macro monthlyDetail>
    select type,count(1) as count,round(sum(value),2) as amount from transactions
    where is_del = 0
    and left(time,7) = :month
    and model =-1
    and account_id = :accountId
    group by type
    order by amount desc
</#macro>

<#-- 获取月度消费明细（按类型汇总） -->
<#macro yearlyDetail>
    select type,count(1) as count,round(sum(value),2) as amount from transactions
    where is_del = 0
    and left(time,4) = :year
    and model =-1
    and account_id = :accountId
    group by type
    order by amount desc
</#macro>
