                    <div class="transfer-form">
                        <form action="transaction" method="POST">

   							<input type="hidden" name="type" value="intrabank">
                            <div class="input-div">
                                <div class="label-div">

                                    <div class="values">
                                        <label for="senderAccount">Sender Account Number:</label>
                                        <select id="senderAccount" name="senderAccount">
		                                    <c:forEach var="accNo" items="${accNos}">
		 										<option value="${accNo}">${accNo}</option>
											</c:forEach>
											</select>
                                    </div>
                                    <div class="values">

                                        <label for="amount">Amount:</label>
                                        <input type="number" id="amount" name="amount">
                                    </div>
                                    <div class="values">
                                        <label for="receiverAccount">Receiver Account Number:</label>
                                        <input type="text" id="receiverAccount" name="receiverAccount">
                                    </div>
                                    <div class="values">
                                        <label for="desc">Description</label>
                                        <textarea name="desc" id="" cols="10" rows="10"></textarea>
                                    </div>
                                    <div class="values">
                                        <label for="password">Password:</label>
                                        <input type="password" id="password" name="password">
                                    </div>

                                </div>
                            </div>
                            <div class="submit-button">
                                <input class="button" type="submit" value="Submit">
                            </div>
                        </form>
                    </div>