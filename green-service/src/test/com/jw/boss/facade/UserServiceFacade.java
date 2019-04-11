package com.yajun.green.facade;

import com.alibaba.dubbo.rpc.protocol.rest.support.ContentType;
import com.yajun.green.facade.dto.UserDTO;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * User: Jack Wang
 * Date: 14-4-9
 * Time: 上午9:20
 */
@Path("/userservice")
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces({ContentType.APPLICATION_JSON_UTF_8, ContentType.TEXT_XML_UTF_8})
public interface UserServiceFacade {

    @GET
    @Path("/getuser")
    public UserDTO getUser();

}
