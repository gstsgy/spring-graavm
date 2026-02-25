<#-- 流水查询 -->
<#macro findList>
    select s.*,o.nick_name as user_name,a.`name` as account_name from transactions s
    left join operator o on s.user_id = o.id and o.effective=1
    left join account_book a on s.account_id = a.id and a.effective=1
    where 1=1
    and s.effective = 1
    and s.account_id = :accountId
    <#if userId??>  and s.user_id = :userId </#if>
    <#if desc??>   AND s.desc LIKE CONCAT('%', :desc, '%')</#if>
    <#if model??>    AND s.model = :model </#if>
    <#if startDate??>    and s.time  >=  :startDate </#if>
    <if test="params.startDate!=null">

    </if>
    <if test="params.endDate!=null">
        and s.time <![CDATA[ <= ]]> #{params.endDate}
    </if>

    <if test="params.types !=null and params.types !='' ">
        and s.type in
        <foreach item="item" index="index" collection="params.types.split(',')" open="(" separator="," close=")">
            #{item}
        </foreach>
    </if>
    order by s.time desc,s.user_id,s.insert_ymd desc

    SELECT * FROM users
    WHERE 1=1
    <#if name??> AND username LIKE :name </#if>
    <#if status??> AND status = :status </#if>
</#macro>

<#-- 定义统计总数的逻辑 -->
<#macro countAll>
    SELECT * FROM btn
    where 1 = 1
    <#if id??>  and id = :id </#if>
</#macro>