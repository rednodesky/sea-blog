package com.sea.dao;

import com.sea.modal.Banner;
import com.sea.modal.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/6/21.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

}
