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
package it.infn.mw.iam.core.oauth.profile.wlcg;

import java.util.Set;

import org.mitre.oauth2.model.ClientDetailsEntity;
import org.mitre.oauth2.model.OAuth2AccessTokenEntity;
import org.springframework.security.oauth2.provider.OAuth2Request;

import com.nimbusds.jwt.JWTClaimsSet.Builder;

import it.infn.mw.iam.api.account.password_reset.error.UserNotFoundError;
import it.infn.mw.iam.config.IamProperties;
import it.infn.mw.iam.core.oauth.profile.common.BaseIdTokenCustomizer;
import it.infn.mw.iam.persistence.model.IamAccount;
import it.infn.mw.iam.persistence.model.IamUserInfo;
import it.infn.mw.iam.persistence.repository.IamAccountRepository;

public class WLCGIdTokenCustomizer extends BaseIdTokenCustomizer {

  private final WLCGGroupHelper groupHelper = new WLCGGroupHelper();

  public WLCGIdTokenCustomizer(IamAccountRepository accountRepo, IamProperties properties) {
    super(accountRepo, properties);
  }

  @Override
  public void customizeIdTokenClaims(Builder idClaims, ClientDetailsEntity client,
      OAuth2Request request, String sub, OAuth2AccessTokenEntity accessToken) {

    IamAccount account = getAccountRepo().findByUuid(sub)
      .orElseThrow(() -> new UserNotFoundError(String.format("No user found for uuid %s", sub)));
    IamUserInfo info = account.getUserInfo();

    Set<String> groupNames = groupHelper.resolveGroupNames(accessToken, info);
    
    if (!groupNames.isEmpty()) {
      idClaims.claim(WLCGGroupHelper.WLCG_GROUPS_SCOPE, groupNames);
    }

    customizeAccountIdTokenClaims(idClaims, client, request, account, accessToken);
  }

}
