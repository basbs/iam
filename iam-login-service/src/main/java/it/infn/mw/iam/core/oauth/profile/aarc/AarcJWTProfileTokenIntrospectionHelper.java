/**
 * Copyright (c) Istituto Nazionale di Fisica Nucleare (INFN). 2016-2019
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
package it.infn.mw.iam.core.oauth.profile.aarc;

import static it.infn.mw.iam.core.oauth.profile.aarc.AarcJWTProfile.AARC_OIDC_CLAIM_AFFILIATION;
import static it.infn.mw.iam.core.oauth.profile.aarc.AarcJWTProfile.AARC_OIDC_CLAIM_ENTITLEMENT;

import java.util.Map;
import java.util.Set;

import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.mitre.oauth2.service.IntrospectionResultAssembler;
import org.mitre.openid.connect.model.UserInfo;

import it.infn.mw.iam.config.IamProperties;
import it.infn.mw.iam.core.oauth.profile.common.BaseIntrospectionHelper;
import it.infn.mw.iam.core.oauth.scope.matchers.ScopeMatcherRegistry;
import it.infn.mw.iam.persistence.model.IamUserInfo;
import it.infn.mw.iam.persistence.repository.UserInfoAdapter;

public class AarcJWTProfileTokenIntrospectionHelper extends BaseIntrospectionHelper {

  protected final AarcUrnHelper aarcUrnHelper;

  public AarcJWTProfileTokenIntrospectionHelper(IamProperties props,
      IntrospectionResultAssembler assembler, ScopeMatcherRegistry scopeMatchersRegistry,
      AarcUrnHelper aarcUrnHelper) {
    super(props, assembler, scopeMatchersRegistry);
    this.aarcUrnHelper = aarcUrnHelper;
  }

  @Override
  public Map<String, Object> assembleIntrospectionResult(OAuth2AccessTokenEntity accessToken,
      UserInfo userInfo, Set<String> authScopes) {

    Map<String, Object> result = getAssembler().assembleFrom(accessToken, userInfo, authScopes);

    if (userInfo != null) {

      IamUserInfo iamUserInfo = ((UserInfoAdapter) userInfo).getUserinfo();

      result.put(NAME, iamUserInfo.getName());
      result.put(GIVEN_NAME, iamUserInfo.getGivenName());
      result.put(FAMILY_NAME, iamUserInfo.getFamilyName());
      result.put(EMAIL, iamUserInfo.getEmail());
      result.put(AARC_OIDC_CLAIM_AFFILIATION, aarcUrnHelper.getOrganisationName());

      if (!iamUserInfo.getGroups().isEmpty()) {

        result.put(AARC_OIDC_CLAIM_ENTITLEMENT, aarcUrnHelper.resolveGroups(iamUserInfo));
      }
    }

    return result;
  }

}
