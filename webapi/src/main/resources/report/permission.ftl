
<#macro reqlog>
    select l.*,o.nick_name as user_name from req_log l
    left join operator o on l.insert_id = o.id and o.is_del=0
    where 1=1
    <#if ip?? && ip != "">  and l.ip = :ip </#if>
    order by l.id desc
</#macro>


