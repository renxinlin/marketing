package com.jgw.supercodeplatform.marketing.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.jgw.supercodeplatform.exception.SuperCodeException;
import com.jgw.supercodeplatform.marketing.common.model.RestResult;


@Component
public class RestTemplateUtil {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 发送get请求返回json数据
	 * @param url
	 * @param params
	 * @param headerMap
	 * @return
	 * @throws SuperCodeException
	 */
	public ResponseEntity<String> getRequestAndReturnJosn(String url,Map<String, Object> params,Map<String, String> headerMap) throws SuperCodeException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeException("sendGetRequestAndReturnJosn参数url不能为空", 500);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromUriString(url);
		if (null!=params && !params.isEmpty()) {
			for(String key:params.keySet()) {
				Object value=params.get(key);
				builder.queryParam(key,  value);
			}
		}

		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> result = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, entity, String.class);
		return result;
	}


	/**
	 * 发送post请求返回json数据
	 * @param url
	 * @param params
	 * @param headerMap
	 * @return
	 * @throws SuperCodeException
	 */
	public ResponseEntity<String> postJsonDataAndReturnJosn(String url,String json,Map<String, String> headerMap) throws SuperCodeException {
		return excuteJsonRequest(url, json, headerMap,HttpMethod.POST);
	}

	/**
	 * 发送PUT请求返回json数据
	 * @param url
	 * @param params
	 * @param headerMap
	 * @return
	 * @throws SuperCodeException
	 */
	public ResponseEntity<String> putJsonDataAndReturnJosn(String url,String json,Map<String, String> headerMap) throws SuperCodeException {
		return excuteJsonRequest(url, json, headerMap,HttpMethod.PUT);
	}


	private ResponseEntity<String> excuteJsonRequest(String url, String json, Map<String, String> headerMap,HttpMethod httpMethod)
			throws SuperCodeException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeException("postJsonDataAndReturnJosn参数url不能为空", 500);
		}
		HttpHeaders headers = new HttpHeaders();
		//默认值 发送json数据
		headers.add("content-Type", "application/json");
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
		ResponseEntity<String> result = restTemplate.exchange(url,
				httpMethod, requestEntity, String.class);
		return result;
	}
	/**
	 * 解决短信发送JSON String乱码问题
	 * @param url
	 * @param json
	 * @param headerMap
	 * @return
	 */
	public RestResult postJsonDataAndReturnJosnObject(String url,Map json,Map<String, String> headerMap)   {
		ResponseEntity<RestResult> restResultResponseEntity = restTemplate.postForEntity(url, json, RestResult.class);
		if(restResultResponseEntity.getStatusCode().value() == HttpStatus.OK.value() && restResultResponseEntity.getBody().getState() == 200){
			return  new RestResult(200, "success", null);
		}else {
			return  new RestResult(500, "短信发送失败", null);
		}
	}


	/**
	 * 上传文件
	 * @param url
	 * @param fileParamName
	 * @param params
	 * @param headerMap
	 * @return
	 * @throws SuperCodeException
	 */
	public ResponseEntity<String> uploadFile(String url,String fileParamName,Map<String, Object> params,Map<String, String> headerMap) throws SuperCodeException {
		if (StringUtils.isBlank(url)) {
			throw new SuperCodeException("postJsonDataAndReturnJosn参数url不能为空", 500);
		}

		Object file=params.remove(fileParamName);
		if (null==file) {
			throw new SuperCodeException("文件不存在", 500);
		}
		FileSystemResource fs = new FileSystemResource((File) file);
		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		param.add(fileParamName, fs);

		if (null!=params && !params.isEmpty()) {
			for(String key:params.keySet()) {
				Object value=params.get(key);
				param.add(key, value);
			}
		}

		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("multipart/form-data");
		headers.setContentType(type);
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.add(key, headerMap.get(key));
			}
		}
		HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(param,headers);

		ResponseEntity<String> responseEntity = restTemplate.exchange(url,
				HttpMethod.POST, httpEntity, String.class);
		return responseEntity;
	}

	public ResponseEntity<String> uploadInputtream(String url,InputStream inputStream,String name,Map<String, String> headerMap) throws FileNotFoundException, IOException {
		MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
		byte[] bytesArray = new byte[(int) inputStream.available()];
		inputStream.read(bytesArray); //read file into bytes[]
		inputStream.close();

		ByteArrayResource contentsAsResource = new ByteArrayResource(bytesArray) {
			@Override
			public String getFilename() {
				return "img";
			}
		};
		paramMap.add("file", contentsAsResource);
		paramMap.add("name", name);

		HttpHeaders headers = new HttpHeaders();
		headers.set("content-type", "multipart/form-data");
		if (null!=headerMap && !headerMap.isEmpty()) {
			for(String key:headerMap.keySet()) {
				headers.set(key, headerMap.get(key));
			}
		}
		HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<MultiValueMap<String, Object>>(paramMap, headers);
		ResponseEntity<String> data=restTemplate.exchange(url,
				HttpMethod.POST, entity, String.class);
		return data;
	}
}
