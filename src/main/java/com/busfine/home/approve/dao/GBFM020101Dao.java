package com.busfine.home.approve.dao;

import com.busfine.home.approve.param.GBFM020101ParamVo;
import com.busfine.home.approve.vo.GBFM020101Vo;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GBFM020101Dao {
    private final SqlSession sql;

    public List<GBFM020101Vo> selectList(GBFM020101ParamVo paramVo) {
        return sql.selectList("GBFM020101Mapper.selectList", paramVo);
    }

    public GBFM020101Vo selectOne(GBFM020101ParamVo paramVo) {
        return sql.selectOne("GBFM020101Mapper.selectOne", paramVo);
    }

    public int insert(GBFM020101Vo vo) {
        return sql.insert("GBFM020101Mapper.insert", vo);
    }

    public int update(GBFM020101Vo vo) {
        return sql.update("GBFM020101Mapper.update", vo);
    }

    public int delete(GBFM020101Vo vo) {
        return sql.delete("GBFM020101Mapper.delete", vo);
    }

}
